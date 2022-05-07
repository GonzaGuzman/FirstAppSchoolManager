package com.zalo.firstAppMVP.util.data

import androidx.room.*
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Completable

@Dao
interface StudentDao {

    @Query("SELECT * FROM student_entity ORDER BY lastName ASC")
    fun getAllStudent(): Single<List<Student>>

    @Query("SELECT * FROM student_entity WHERE id =:id")
    fun getById(id: Int): Single<Student>

    @Update
    fun update(student: Student): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student): Completable

    @Delete
    fun delete(student: Student): Single<Unit>
}
