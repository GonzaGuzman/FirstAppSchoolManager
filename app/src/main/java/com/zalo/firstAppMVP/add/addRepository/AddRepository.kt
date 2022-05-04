package com.zalo.firstAppMVP.add.addRepository


<<<<<<< HEAD
import com.zalo.firstAppMVP.util.data.StudentDataBase
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.core.Completable

class AddRepository(private val dataBaseStudent: StudentDataBase) {

    fun insert(student: Student): Completable {
=======
import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.homeActivity.Student
import io.reactivex.rxjava3.core.Completable

class AddRepository( private val dataBaseStudent:StudentDataBase) {

    fun insert(student: Student): Completable{
>>>>>>> main
        return dataBaseStudent.studentDao().insert(student)
    }
}