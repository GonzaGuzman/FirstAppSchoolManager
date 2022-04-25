package com.zalo.firstAppMVP.add.addRepository


import com.zalo.firstAppMVP.util.data.StudentDataBase
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.core.Completable

class AddRepository( private val dataBaseStudent: StudentDataBase) {

    fun insert(student: Student): Completable{
        return dataBaseStudent.studentDao().insert(student)
    }
}