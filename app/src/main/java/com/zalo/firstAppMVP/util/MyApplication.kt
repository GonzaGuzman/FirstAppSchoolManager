package com.zalo.firstAppMVP.util

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.zalo.firstAppMVP.data.StudentDataBase

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