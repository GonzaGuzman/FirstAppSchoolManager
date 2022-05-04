package com.zalo.firstAppMVP.detail.detailRepository

<<<<<<< HEAD
import com.zalo.firstAppMVP.util.data.StudentDataBase
import com.zalo.firstAppMVP.util.dataClassStudent.Student
=======
import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>> main
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


class DetailRepository(private val dataBaseStudent: StudentDataBase) {

<<<<<<< HEAD
=======
/*
    private val allStudent: Single<List<Student>> = dataBaseStudent.studentDao().getAllStudent()

    fun getAllStudent() = allStudent
*/
>>>>>>> main

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
