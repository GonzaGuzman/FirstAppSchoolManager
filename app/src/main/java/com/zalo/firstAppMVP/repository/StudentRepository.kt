package com.zalo.firstAppMVP.repository


import android.util.Log
import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.home.Student
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class StudentRepository(private val dataBaseStudent: StudentDataBase) : repo {
    private val allStudent: Single<List<Student>>

    init {
        allStudent = dataBaseStudent.studentDao().getAllStudent()
    }

    override fun getAllStudent() = allStudent
    override fun getById(id: Int): Single<Student> {
        TODO("Not yet implemented")
    }
/*
    override fun getById(id: Int): Single<> {
         dataBaseStudent.studentDao().getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                      it
                   }, {
                error -> Log.e(TAG, "ERROR", error)
            })

    }*/

    override fun update(student: Student): Completable {
        TODO("Not yet implemented")
    }

    override fun insert(student: Student): Completable {
        TODO("Not yet implemented")
    }

    override fun delete(student: Student): Single<Unit> {
        TODO("Not yet implemented")
    }
}
