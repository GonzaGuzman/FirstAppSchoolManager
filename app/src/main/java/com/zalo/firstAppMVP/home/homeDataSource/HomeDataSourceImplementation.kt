package com.zalo.firstAppMVP.home.homeDataSource


import com.zalo.firstAppMVP.home.homeRepository.HomeRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeDataSourceImplementation(private val homeRepository: HomeRepository) : HomeDataSource {

    override fun getSchoolName() = homeRepository.getRepositorySchoolName()

    override fun getSchoolTypeEducation() = homeRepository.getRepositorySchoolTypeEducation()

    override fun getListOfStudent(
        onSuccess: (response: List<Student>) -> Unit,
        onError: (Throwable) -> Unit,
    ): Disposable {
        return homeRepository.getAllStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it) },
                { onError(it) }
            )
    }
}