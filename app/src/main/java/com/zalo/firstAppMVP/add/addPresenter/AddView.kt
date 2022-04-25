package com.zalo.firstAppMVP.add.addPresenter

interface AddView {
    fun resetView()
    fun setErrorName(error: Boolean)
    fun setErrorLastName(error: Boolean)
    fun setErrorAge(error: Boolean)
    fun setName()
    fun setLastName()
    fun setAge()
    fun navigateTo()
    fun showErrorSnackBar(message: String)
    fun showSuccessSnackBar(message: String)
    fun validationForAdd(): Boolean
    fun getGender(): String


}