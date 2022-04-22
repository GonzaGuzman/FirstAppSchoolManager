package com.zalo.firstAppMVP.repository

import com.zalo.firstAppMVP.home.Student
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface repo {

    fun getAllStudent(): Single<List<Student>>

    fun getById(id: Int): Single<Student>

    fun update(student: Student): Completable

    fun insert(student: Student): Completable

    fun delete(student: Student): Single<Unit>

}