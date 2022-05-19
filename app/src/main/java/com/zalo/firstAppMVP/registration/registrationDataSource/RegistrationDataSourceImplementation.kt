package com.zalo.firstAppMVP.registration.registrationDataSource


import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationDataSourceImplementation(private val registrationRepository: RegistrationRepository) :
    RegistrationDataSource {

    override fun getSchoolNameOfShared() = registrationRepository.getRepositorySchoolName()

    override fun getTypeEducationOfShared() =
        registrationRepository.getRepositorySchoolTypeEducation()

    override fun setSchoolNameInShared(school: String) =
        registrationRepository.setRepositorySchoolName(school)

    override fun setTypeEducationInShared(type: String) =
        registrationRepository.setRepositorySchoolTypeEducation(type)

    override fun wipe() = registrationRepository.wipeRepository()

    override fun getSchoolsList(
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
    override fun postSchool(
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
