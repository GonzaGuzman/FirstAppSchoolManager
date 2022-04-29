package com.zalo.firstAppMVP.detail.detailPresenter

import com.zalo.firstAppMVP.util.dataClassStudent.Student

interface DetailView {

    fun initView(student: Student)
    fun enabledViews()
    fun disabledViews()
    fun navigateTo()
    fun showAlertDeleteDialog()
    fun showSnackBar(message : String)
    fun dialogDismiss()
    fun getUpdateName()
    fun getUpdateLastName()
    fun getUpdateAge()
}