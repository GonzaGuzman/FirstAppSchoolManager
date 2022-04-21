package com.zalo.firstAppMVP.presenter

import com.zalo.firstAppMVP.home.Student

interface DetailView {

    fun initView(student : Student)
    fun enabledViews()
    fun disabledViews()
    fun edit()
    fun save()
    fun delete()
    fun navigateTo()

}