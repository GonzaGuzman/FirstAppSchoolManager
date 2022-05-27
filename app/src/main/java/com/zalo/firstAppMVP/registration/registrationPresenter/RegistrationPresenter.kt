package com.zalo.firstAppMVP.registration.registrationPresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationDataSource.RegistrationDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RegistrationPresenter(
    private val view: RegistrationsView,
    private val registrationDataSource: RegistrationDataSource,
    private val resources: Resources,
    private val registrationState: RegistrationState,
) : RegistrationActions {
    private val compositeDisposable = CompositeDisposable()

    override fun initView() {
        val nameSchool = registrationDataSource.getSchoolNameOfShared()
        val typeEducation = registrationDataSource.getTypeEducationOfShared()
        if (nameSchool.isNotEmpty() && typeEducation.isNotEmpty()
        ) {
            view.initComponent(nameSchool, typeEducation)
            view.viewDisabled()
        } else {
            getSchools()
        }
    }

    override fun setSchoolName(nameSchool: String) {
        registrationDataSource.setSchoolNameInShared(nameSchool)
    }

    override fun setTypeEducation(currentTypeEducation: String) {
        registrationDataSource.setTypeEducationInShared(currentTypeEducation)
    }

    override fun buttonContinueClicked() {
        val nameSchool = registrationDataSource.getSchoolNameOfShared()
        val typeEducation = registrationDataSource.getTypeEducationOfShared()
        if (nameSchool.isEmpty()) {
            view.setErrorName(true)
        } else {
            view.setErrorName(false)
            val auxSchool = School(nameSchool, typeEducation)
            if (registrationState.listOfSchools.get()?.containsKey(auxSchool.name) == false) {
                postSchool(auxSchool)
            }
            view.navigateTo()
        }
    }

    override fun buttonCloseSessionClicked() {
        view.showAlertCloseSession()
    }

    override fun getSchools() {
        compositeDisposable.add(
            registrationDataSource.getSchoolsList(
                {
                    val listResponse = mutableMapOf<String, String>()
                    it.schools.forEach { it1 -> listResponse[it1.name] = it1.type }
                    registrationState.listOfSchools.set(listResponse)
                    getArrayOfSchoolName(it)
                    registrationState.listOfNamesSchools.get()?.let { it1 -> view.listAdapter(it1) }
                },
                { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
            )
        )
    }

    override fun postSchool(school: School) {
        val errorMessage = "POST FAIL"
        compositeDisposable.add(
            registrationDataSource.postSchool(
                school,
                { println(it.msm.uppercase()) },
                { println(errorMessage.uppercase())}
            )
        )
    }

    override fun getArrayOfSchoolName(list: Schools) {
        val listOfNames: ArrayList<String> = ArrayList()
        list.schools.forEach { listOfNames.add(it.name) }
        listOfNames.sort()
        registrationState.listOfNamesSchools.set(listOfNames)
    }


    override fun validateTypeEducation(name: String) {
        val listSchools = registrationState.listOfSchools.get()
        if (listSchools?.contains(name) == true)
            view.validateRadioButton(listSchools.getValue(name))
    }

    override fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    override fun onPositiveButtonClicked() {
        view.viewEnabled()
        registrationDataSource.wipe()
        getSchools()
        registrationDataSource.setTypeEducationInShared(resources.getString(R.string.primaryEducation))
        view.showSnackBar(resources.getString(R.string.closed_session))
    }
}