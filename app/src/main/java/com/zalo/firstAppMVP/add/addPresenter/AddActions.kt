package com.zalo.firstAppMVP.add.addPresenter

interface AddActions {
    fun buttonAddClicked()
    fun buttonCancelClicked()

    fun setName(name: String)
    fun setLastName(lastName: String)
    fun setAge(age: Int)
    fun reset()
    fun setGender(gender: String)
    fun dataStudentIsNotEmpty(): Boolean
}