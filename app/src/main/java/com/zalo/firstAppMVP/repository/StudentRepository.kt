package com.zalo.firstAppMVP.repository

import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.home.Student
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class StudentRepository(private val dataBaseStudent: StudentDataBase) : repo {
    private val allStudent: Single<List<Student>> = dataBaseStudent.studentDao().getAllStudent()

    override fun getAllStudent() = allStudent

    override fun getById(id: Int): Single<Student> {
        return dataBaseStudent.studentDao().getById(id)
    }

    override fun update(student: Student): Completable {
        return dataBaseStudent.studentDao().update(student)
    }

    override fun insert(student: Student): Completable {
        return dataBaseStudent.studentDao().insert(student)
    }

    override fun delete(student: Student): Single<Unit> {
        return dataBaseStudent.studentDao().delete(student)
    }
}
