package com.zalo.myrecyclerview.home

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student_entity")
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var lastName: String,
    var age: Int,
    var gender: String,
)
