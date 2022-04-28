package com.zalo.firstAppMVP.home.homeRepository

import com.zalo.firstAppMVP.util.data.StudentDataBase
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.util.sharedPreferences.MySharedPreferences
import io.reactivex.rxjava3.core.Single

class HomeRepository(private val dataBaseStudent: StudentDataBase) {

    fun getRepositorySchoolName() =
        MySharedPreferences.prefs.getString(MySharedPreferences.SCHOOL_NAME, "") ?: ""

    fun getRepositorySchoolTypeEducation() =
        MySharedPreferences.prefs.getString(MySharedPreferences.TYPE_EDUCATION, "") ?: ""

    fun getAllStudent(): Single<List<Student>> {
        return dataBaseStudent.studentDao().getAllStudent()
    }
}