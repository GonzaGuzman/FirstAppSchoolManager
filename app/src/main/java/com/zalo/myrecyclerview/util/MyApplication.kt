package com.zalo.myrecyclerview.util

import android.app.Application
import android.content.SharedPreferences

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = applicationContext.getSharedPreferences("schoolPreferences", 0)
    }

    companion object {
        lateinit var preferences: SharedPreferences

    }
}