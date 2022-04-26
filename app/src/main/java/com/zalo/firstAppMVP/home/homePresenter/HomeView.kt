package com.zalo.firstAppMVP.home.homePresenter

import com.zalo.firstAppMVP.util.dataClassStudent.Student

interface HomeView {
    fun showSchoolName(schoolName: String)
    fun showSchoolTypeEducation(typeEducation: String)
    fun navigateTo()
    fun loadRecycler(studentList: List<Student>)
    fun showSuccessSnackBar(message: String)
    fun showErrorSnackBar(message: String)
}