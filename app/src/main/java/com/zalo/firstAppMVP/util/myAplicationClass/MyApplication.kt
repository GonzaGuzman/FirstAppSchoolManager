package com.zalo.firstAppMVP.util.myAplicationClass

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
<<<<<<< HEAD:app/src/main/java/com/zalo/firstAppMVP/util/myAplicationClass/MyApplication.kt
import com.zalo.firstAppMVP.util.data.StudentDataBase
=======
import com.zalo.firstAppMVP.data.StudentDataBase
import dagger.hilt.android.HiltAndroidApp
>>>>>>> main:app/src/main/java/com/zalo/firstAppMVP/util/MyApplication.kt

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences("schoolPreferences", 0)

        dataBase =
            Room.databaseBuilder(this, StudentDataBase::class.java, "studentDB").build()
    }

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var dataBase: StudentDataBase
    }
}