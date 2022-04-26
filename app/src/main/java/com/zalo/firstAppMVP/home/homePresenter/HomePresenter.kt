package com.zalo.firstAppMVP.home.homePresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.home.homeRepository.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(
    private val view: HomeView,
    private val homeRepository: HomeRepository,
    private val resources: Resources,
) : HomeActions {

    private val compositeDisposable = CompositeDisposable()

    override fun buttonAddClicked() {
        view.navigateTo()
    }

    override fun initComponent() {
        compositeDisposable.add(
            homeRepository.getAllStudent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.loadRecycler(it)
                }, { error ->
                    view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                })
        )
    }

    override fun initSchoolDate() {
        view.showSchoolName(homeRepository.schoolName)
        view.showSchoolTypeEducation(homeRepository.typeEducation)
    }

}