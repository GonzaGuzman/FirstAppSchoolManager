package com.zalo.firstAppMVP.presenter

interface AddView {
    fun addStudent()
    fun cancel()
    fun resetView()
    fun setErrorName(error: Boolean)
    fun setErrorLastName(error: Boolean)
    fun setErrorAge(error: Boolean)
    fun updateName()
    fun updateLastName()
    fun updateAge()

}