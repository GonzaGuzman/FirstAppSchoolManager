package com.zalo.firstAppMVP.detail.detailPresenter

<<<<<<< HEAD
import com.zalo.firstAppMVP.util.dataClassStudent.Student
=======
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>> main

interface DetailView {

    fun initView(student: Student)
    fun enabledViews()
    fun disabledViews()
    fun navigateTo()
    fun showAlertDeleteDialog()
<<<<<<< HEAD
    fun showSnackBar(message : String)
=======
    fun showErrorSnackBar(message: String)
    fun showSuccessSnackBar(message : String)
>>>>>>> main
    fun dialogDismiss()
    fun getUpdateName()
    fun getUpdateLastName()
    fun getUpdateAge()
}