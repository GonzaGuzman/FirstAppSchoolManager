package com.zalo.firstAppMVP.detail.detailRepository

import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.homeActivity.Student
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


class DetailRepository(private val dataBaseStudent: StudentDataBase) {

/*
    private val allStudent: Single<List<Student>> = dataBaseStudent.studentDao().getAllStudent()

    fun getAllStudent() = allStudent
*/

    fun getById(id: Int): Single<Student> {
        return dataBaseStudent.studentDao().getById(id)
    }

    fun update(student: Student): Completable {
        return dataBaseStudent.studentDao().update(student)
    }

    fun delete(student: Student): Single<Unit> {
        return dataBaseStudent.studentDao().delete(student)
    }
}
