package com.zalo.firstAppMVP.registration.registrationPresenter


interface RegistrationsView {
    fun viewDisabled()
    fun viewEnabled()
    fun initComponent(nameSchool: String, typeEducation: String)
    fun setErrorName(error: Boolean)
    fun getSchoolName()
    fun getTypeEducation(): String
    fun showAlertCloseSession()
    fun dialogDismiss()
    fun showSnackBar(message: String)
    fun listAdapter(schoolsNamesList: ArrayList<String>)
    fun validateRadioButton(id: String)
    fun navigateTo(message: String)
}