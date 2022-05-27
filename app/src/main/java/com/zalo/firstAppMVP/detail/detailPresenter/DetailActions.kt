package com.zalo.firstAppMVP.detail.detailPresenter

interface DetailActions {

    fun buttonEditClicked()
    fun buttonRemoveClicked()
    fun buttonSaveClicked()
    fun setGender(gender: String)
    fun setName(name: String)
    fun setLastName(lastName: String)
    fun setAge(age: Int)
    fun getStudentById(id: Int)
    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
}