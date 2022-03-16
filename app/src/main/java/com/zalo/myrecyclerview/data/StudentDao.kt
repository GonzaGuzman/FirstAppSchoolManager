package com.zalo.myrecyclerview.data

import androidx.room.*
import com.zalo.myrecyclerview.home.Student


@Dao
interface StudentDao {

    @Query("SELECT * FROM student_entity ORDER BY lastName ASC")
    fun getAllStudent(): List<Student>

    @Query("SELECT * FROM student_entity WHERE id =:id")
    fun getById(id: Int): Student

    @Update
    fun upDate(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student)

    @Delete
    fun delete(student: Student)
}
