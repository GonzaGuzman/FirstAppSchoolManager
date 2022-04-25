package com.zalo.firstAppMVP.registration.registrationPresenter


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
    fun showSuccessSnackBar(message: String)
}