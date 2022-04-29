package com.zalo.firstAppMVP.registration.registrationPresenter

import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationFragment.RegistrationFragment


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
    fun showSnackBar(message: String)
    fun listAdapter(schoolsNamesList: ArrayList<String>)
    fun validateRadioButton(id: String)
}