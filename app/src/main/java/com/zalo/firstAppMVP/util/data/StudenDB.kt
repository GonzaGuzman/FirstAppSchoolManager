package com.zalo.firstAppMVP.util.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalo.firstAppMVP.util.dataClassStudent.Student

@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDataBase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}