package com.zalo.firstAppMVP.registration.registrationPresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
<<<<<<< HEAD
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
=======
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository

class RegistrationPresenter(
    private val view: RegistrationsView,
    private val registrationRepository: RegistrationRepository,
    private val resources: Resources,
) : RegistrationActions {

    fun initView() {
        if (registrationRepository.schoolName.isNotEmpty() && registrationRepository.typeEducation.isNotEmpty()) {
            view.initComponent(registrationRepository.schoolName,
                registrationRepository.typeEducation)
            view.viewDisabled()
>>>>>>> main
        }
    }

    fun setSchoolName(nameSchool: String) {
<<<<<<< HEAD
        registrationDataSource.setSchoolNameInShared(nameSchool)
    }

    private fun setTypeEducation() {
        val currentTypeEducation = view.getTypeEducation()
        registrationDataSource.setTypeEducationInShared(currentTypeEducation)
    }

    override fun buttonContinueClicked() {
        if (registrationDataSource.getSchoolNameOfShared().isEmpty()) {
=======
        registrationRepository.schoolName = nameSchool
    }

    private fun setTypeEducation() {
        registrationRepository.typeEducation = view.getTypeEducation()
    }

    override fun buttonContinueClicked() {
        if (registrationRepository.schoolName.isEmpty()) {
>>>>>>> main
            view.setErrorName(true)
        } else {
            view.setErrorName(false)
            setTypeEducation()
            view.navigateTo()
        }
    }

    override fun buttonCloseSessionClicked() {
        view.showAlertCloseSession()
    }

<<<<<<< HEAD
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

=======
>>>>>>> main
    fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    fun onPositiveButtonClicked() {
        view.viewEnabled()
<<<<<<< HEAD
        registrationDataSource.wipe()
        getSchools()
        view.showSnackBar(resources.getString(R.string.closed_session))
    }

=======
        registrationRepository.wipe()
        view.showSuccessSnackBar(resources.getString(R.string.closed_session))
    }


>>>>>>> main
}