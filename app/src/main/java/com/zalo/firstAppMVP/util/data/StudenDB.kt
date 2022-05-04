package com.zalo.firstAppMVP.util.data

import androidx.room.Database
import androidx.room.RoomDatabase
<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/data/StudenDB.kt
import com.zalo.firstAppMVP.util.dataClassStudent.Student
=======
import com.zalo.firstAppMVP.homeActivity.Student
>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/data/StudenDB.kt

@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDataBase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}