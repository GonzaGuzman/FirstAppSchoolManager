package com.zalo.firstAppMVP.add.addDataSource

import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable

interface AddDataSource {

    fun insertNewStudent(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

}