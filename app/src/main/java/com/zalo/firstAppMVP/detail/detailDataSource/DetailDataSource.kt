package com.zalo.firstAppMVP.detail.detailDataSource

import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable

interface DetailDataSource {

    fun getStudentById(
        id: Int,
        onSuccess: (responsive: Student) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

    fun updateDataStudent(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

    fun deleteStudentOfDataBase(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

}