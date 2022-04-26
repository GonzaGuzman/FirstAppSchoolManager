package com.zalo.firstAppMVP.home.homeRepository

import com.zalo.firstAppMVP.util.data.StudentDataBase
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.util.sharedPreferences.MySharedPreferences
import io.reactivex.rxjava3.core.Single

class HomeRepository(private val dataBaseStudent: StudentDataBase) {
    var schoolName: String
        get() = MySharedPreferences.prefs.getString(MySharedPreferences.SCHOOL_NAME, "") ?: ""
        set(value) = MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.SCHOOL_NAME, value).apply()

    var typeEducation: String
        get() = MySharedPreferences.prefs.getString(MySharedPreferences.TYPE_EDUCATION, "") ?: ""
        set(value) = MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.TYPE_EDUCATION, value).apply()

    fun getAllStudent(): Single<List<Student>> {
        return dataBaseStudent.studentDao().getAllStudent()
    }
}