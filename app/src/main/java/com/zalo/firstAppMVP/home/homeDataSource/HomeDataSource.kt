package com.zalo.firstAppMVP.home.homeDataSource

import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable

interface HomeDataSource {

    fun getSchoolName(): String
    fun getSchoolTypeEducation(): String
    fun getListOfStudent(
        onSuccess: (response: List<Student>) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable
}
