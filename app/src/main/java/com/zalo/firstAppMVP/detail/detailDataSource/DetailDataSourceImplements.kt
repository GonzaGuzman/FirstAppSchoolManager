package com.zalo.firstAppMVP.detail.detailDataSource

import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailDataSourceImplements(private val detailRepository: DetailRepository) : DetailDataSource {

    override fun getStudentById(
        id: Int,
        onSuccess: (responsive: Student) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }

    override fun updateDataStudent(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.update(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess() },
                { onError(it) }
            )
    }

    override fun deleteStudentOfDataBase(
        student: Student,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return detailRepository.delete(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess() },
                { onError(it) }
            )
    }
}