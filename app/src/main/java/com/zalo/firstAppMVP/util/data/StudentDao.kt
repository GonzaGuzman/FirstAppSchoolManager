package com.zalo.firstAppMVP.util.data

import androidx.room.*
<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/data/StudentDao.kt
import com.zalo.firstAppMVP.util.dataClassStudent.Student
=======
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/data/StudentDao.kt
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
