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

    fun initView() {
        if (registrationDataSource.getSchoolNameOfShared()
                .isNotEmpty() && registrationDataSource.getTypeEducationOfShared()
                .isNotEmpty()
        ) {
            view.initComponent(registrationDataSource.getSchoolNameOfShared(),
                registrationDataSource.getTypeEducationOfShared())
            view.viewDisabled()
        } else {
            getSchools()
        }
    }

    fun setSchoolName(nameSchool: String) {
        registrationDataSource.setSchoolNameInShared(nameSchool)
    }

    private fun setTypeEducation() {
        val currentTypeEducation = view.getTypeEducation()
        registrationDataSource.setTypeEducationInShared(currentTypeEducation)
    }

    override fun buttonContinueClicked() {
        if (registrationDataSource.getSchoolNameOfShared().isEmpty()) {
            view.setErrorName(true)
        } else {
            view.setErrorName(false)
            setTypeEducation()
            val aux = School(registrationDataSource.getSchoolNameOfShared(),
                registrationDataSource.getTypeEducationOfShared())
            if (!listOfSchools.containsKey(aux.name)) {
                postSchool(aux)
            } else
                view.navigateTo(resources.getString(R.string.welcome).uppercase())


        }


    }

    override fun buttonCloseSessionClicked() {
        view.showAlertCloseSession()
    }

    private fun getSchools() {
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

    private fun postSchool(school: School) {
        compositeDisposable.add(
            registrationDataSource.postSchool(
                school,
                { responsive -> view.navigateTo(responsive.msm.uppercase()) },
                { error -> view.navigateTo(error.toString().uppercase()) }
            )
        )
    }

    private fun getArrayOfSchoolName(list: Schools): ArrayList<String> {
        val listOfNames: ArrayList<String> = ArrayList()
        list.schools.forEach { listOfNames.add(it.name) }
        return listOfNames
    }


    fun validateTypeEducation(name: String) {
        if (listOfSchools.contains(name)) {
            view.validateRadioButton(listOfSchools.getValue(name))
        }
    }

    fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    fun onPositiveButtonClicked() {
        view.viewEnabled()
        registrationDataSource.wipe()
        getSchools()
        view.showSnackBar(resources.getString(R.string.closed_session))
    }

}