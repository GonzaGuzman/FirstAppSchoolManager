package com.zalo.firstAppMVP.registration.registrationDataSource

import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import io.reactivex.rxjava3.disposables.Disposable

interface RegistrationDataSource {

    fun getSchoolNameOfShared(): String
    fun getTypeEducationOfShared(): String
    fun setSchoolNameInShared(school: String)
    fun setTypeEducationInShared(type: String)
    fun wipe()

    fun getSchoolsList(
        onSuccess: (responsive: Schools) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable

    fun postSchool(
        school: School,
        onSuccess: (responsive: ResponseNetwork) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable


}


