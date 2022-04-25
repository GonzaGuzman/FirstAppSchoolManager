package com.zalo.firstAppMVP.util.sharedPreferences

import com.zalo.firstAppMVP.util.myAplicationClass.MyApplication

class MySharedPreferences {
    var schoolName: String
        get() = prefs.getString(SCHOOL_NAME, "") ?: ""
        set(value) = prefs.edit().putString(SCHOOL_NAME, value).apply()

    var typeEducation: String
        get() = prefs.getString(TYPE_EDUCATION, "") ?: ""
        set(value) = prefs.edit().putString(TYPE_EDUCATION, value).apply()


    fun wipe() {
        prefs.edit().clear().apply()
    }

    companion object {
        val prefs = MyApplication.preferences
        const val SCHOOL_NAME = "schoolName"
        const val TYPE_EDUCATION = "typeEducation"
    }
}

