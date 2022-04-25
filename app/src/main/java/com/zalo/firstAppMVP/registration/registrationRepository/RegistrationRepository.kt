package com.zalo.firstAppMVP.registration.registrationRepository

import com.zalo.firstAppMVP.util.MySharedPreferences

class RegistrationRepository {

    var schoolName: String
        get() = MySharedPreferences.prefs.getString(MySharedPreferences.SCHOOL_NAME, "") ?: ""
        set(value) = MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.SCHOOL_NAME, value).apply()

    var typeEducation: String
        get() = MySharedPreferences.prefs.getString(MySharedPreferences.TYPE_EDUCATION, "") ?: ""
        set(value) = MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.TYPE_EDUCATION, value).apply()

    fun wipe() {
        MySharedPreferences.prefs.edit().clear().apply()
    }

}