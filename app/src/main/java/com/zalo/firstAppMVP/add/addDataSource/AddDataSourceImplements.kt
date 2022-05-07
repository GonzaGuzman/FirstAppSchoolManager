package com.zalo.firstAppMVP.add.addDataSource

import com.zalo.firstAppMVP.add.addRepository.AddRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddDataSourceImplements(private val addRepository: AddRepository): AddDataSource {

    override fun insertNewStudent(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return addRepository.insert(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess() },
                {
                    onError(it)
                })
    }

}
