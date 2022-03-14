package com.zalo.myrecyclerview.util

class MySharedPreferences {
    var schoolName: String
        get() = prefs.getString(SCHOOL_NAME, "") ?: ""
        set(value) = prefs.edit().putString(SCHOOL_NAME, value).apply()

    var isPrimary: Boolean
        get() = prefs.getBoolean(IS_PRIMARY, false)
        set(value) = prefs.edit().putBoolean(IS_PRIMARY, value).apply()

    var isHighSchool: Boolean
        get() = prefs.getBoolean(IS_HIGHSCHOOL, false)
        set(value) = prefs.edit().putBoolean(IS_HIGHSCHOOL, value).apply()

fun wipe(){
    prefs.edit().clear().apply()
}

    companion object {
        val prefs = MyApplication.preferences
        const val SCHOOL_NAME = "school_name"
        const val IS_PRIMARY ="isPrimary"
        const val IS_HIGHSCHOOL = "isHighSchool"

    }
}