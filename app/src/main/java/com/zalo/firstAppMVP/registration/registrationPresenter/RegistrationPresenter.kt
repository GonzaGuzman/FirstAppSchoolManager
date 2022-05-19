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
) : RegistrationActions {
    private val listOfSchools = mutableMapOf<String, String>()
    private val compositeDisposable = CompositeDisposable()

    override fun initView() {
        val nameSchool = registrationDataSource.getSchoolNameOfShared()
        val typeEducation = registrationDataSource.getTypeEducationOfShared()
        if (nameSchool.isNotEmpty() && typeEducation.isNotEmpty()
        ) {
            view.initComponent(nameSchool,typeEducation)
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
            if (!listOfSchools.containsKey(auxSchool.name)) {
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
                    it.schools.forEach { it1 -> listOfSchools[it1.name] = it1.type }
                    val listSchoolsNames: ArrayList<String> = getArrayOfSchoolName(it)
                    view.listAdapter(listSchoolsNames)
                },
                { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
            )
        )
    }

   override fun postSchool(school: School) {
        compositeDisposable.add(
            registrationDataSource.postSchool(
                school,
                { responsive -> println(responsive.msm.uppercase()) },
                { error -> println(error.toString().uppercase()) }
            )
        )
    }

   override fun getArrayOfSchoolName(list: Schools): ArrayList<String> {
        val listOfNames: ArrayList<String> = ArrayList()
        list.schools.forEach { listOfNames.add(it.name) }
        return listOfNames
    }


    override fun validateTypeEducation(name: String) {
        if (listOfSchools.contains(name)) {
            view.validateRadioButton(listOfSchools.getValue(name))
        }
    }

    override fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    override fun onPositiveButtonClicked() {
        view.viewEnabled()
        registrationDataSource.wipe()
        getSchools()
        setTypeEducation(resources.getString(R.string.primaryEducation))
        view.showSnackBar(resources.getString(R.string.closed_session))
    }
}