package com.zalo.firstAppMVP.registration.registrationDataSource


import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationDataSource(private val registrationRepository: RegistrationRepository) {

    fun getSchoolNameOfShared() = registrationRepository.getRepositorySchoolName()

    fun getTypeEducationOfShared() = registrationRepository.getRepositorySchoolTypeEducation()

    fun setSchoolNameInShared(school: String) =
        registrationRepository.setRepositorySchoolName(school)

    fun setTypeEducationInShared(type: String) =
        registrationRepository.setRepositorySchoolTypeEducation(type)

    fun wipe() = registrationRepository.wipeRepository()

    fun getSchoolsList(
        onSuccess: (responsive: Schools) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return registrationRepository.getSchools()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }


    fun postSchool(
        school: School,
        onSuccess: (responsive: ResponseNetwork) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return registrationRepository.postNewSchool(school)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }
}
