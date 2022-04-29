package com.zalo.firstAppMVP.home.homePresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.home.homeDataSource.HomeDataSource
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomePresenter(
    private val view: HomeView,
    private val homeDataSource: HomeDataSource,
    private val resources: Resources,
) : HomeActions {

    private val compositeDisposable = CompositeDisposable()

    override fun buttonAddClicked() {
        view.navigateTo()
    }

    override fun initComponent() {
        compositeDisposable.add(
            homeDataSource.getListOfStudent(
                { response: List<Student> ->
                    view.loadRecycler(response)
                }, { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
            )
        )
    }

    override fun initSchoolDate() {
        view.showSchoolName(homeDataSource.getSchoolName())
        view.showSchoolTypeEducation(homeDataSource.getSchoolTypeEducation())
    }

}