package com.zalo.firstAppMVP.detail.detailPresenter

import com.zalo.firstAppMVP.homeActivity.Student

interface DetailView {

    fun initView(student: Student)
    fun enabledViews()
    fun disabledViews()
    fun navigateTo()
    fun showAlertDeleteDialog()
    fun showErrorSnackBar(message: String)
    fun showSuccessSnackBar(message : String)
    fun dialogDismiss()
    fun getUpdateName()
    fun getUpdateLastName()
    fun getUpdateAge()
}