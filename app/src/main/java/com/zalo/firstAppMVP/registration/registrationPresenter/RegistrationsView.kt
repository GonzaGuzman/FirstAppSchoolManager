package com.zalo.firstAppMVP.registration.registrationPresenter

<<<<<<< HEAD
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationFragment.RegistrationFragment

=======
>>>>>>> main

interface RegistrationsView {
    fun viewDisabled()
    fun viewEnabled()
    fun initComponent(nameSchool: String, typeEducation: String)
    fun setErrorName(error: Boolean)
    fun getSchoolName()
    fun getTypeEducation(): String
    fun navigateTo()
    fun showAlertCloseSession()
    fun dialogDismiss()
<<<<<<< HEAD
    fun showSnackBar(message: String)
    fun listAdapter(schoolsNamesList: ArrayList<String>)
    fun validateRadioButton(id: String)
=======
    fun showSuccessSnackBar(message: String)
>>>>>>> main
}