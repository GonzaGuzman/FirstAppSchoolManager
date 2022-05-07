package com.zalo.firstAppMVP.detail.detailPresenter

import com.zalo.firstAppMVP.util.dataClassStudent.Student

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
    fun setId(id: Int)
    fun setAll(student: Student)
    fun getAll(): Student
}