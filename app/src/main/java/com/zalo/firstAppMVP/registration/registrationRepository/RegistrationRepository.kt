package com.zalo.firstAppMVP.registration.registrationRepository

import com.zalo.firstAppMVP.util.sharedPreferences.MySharedPreferences

class RegistrationRepository {
    fun setRepositorySchoolName(name: String) {
        MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.SCHOOL_NAME, name).apply()
    }

    fun getRepositorySchoolName() =
        MySharedPreferences.prefs.getString(MySharedPreferences.SCHOOL_NAME, "") ?: ""

    fun setRepositorySchoolTypeEducation(typeEducation: String) {
        MySharedPreferences.prefs.edit()
            .putString(MySharedPreferences.TYPE_EDUCATION, typeEducation).apply()
    }

    fun getRepositorySchoolTypeEducation() = MySharedPreferences.prefs.getString(MySharedPreferences.TYPE_EDUCATION, "") ?: ""

    fun wipeRepository() {
        MySharedPreferences.prefs.edit().clear().apply()
    }

}