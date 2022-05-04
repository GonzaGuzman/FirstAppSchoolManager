package com.zalo.firstAppMVP.registration.registrationRepository

<<<<<<< HEAD
import com.zalo.firstAppMVP.network.APIServiceImpl
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.util.sharedPreferences.MySharedPreferences
import io.reactivex.rxjava3.core.Single

class RegistrationRepository (private val apiService: APIServiceImpl) {
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

    fun getSchools():Single<Schools>{
        return apiService.getSchools()
    }

=======
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

>>>>>>> main
}