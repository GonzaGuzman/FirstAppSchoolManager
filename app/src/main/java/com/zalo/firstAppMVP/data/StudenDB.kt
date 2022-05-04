package com.zalo.firstAppMVP.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalo.firstAppMVP.homeActivity.Student

@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDataBase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}