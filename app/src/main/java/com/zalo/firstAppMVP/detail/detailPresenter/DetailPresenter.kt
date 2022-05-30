package com.zalo.firstAppMVP.detail.detailPresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.detail.detailDataSource.DetailDataSource
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DetailPresenter(
    private val view: DetailView,
    private val detailDataSource: DetailDataSource,
    private val resources: Resources,
    private val detailState: DetailState,
) : DetailActions {

    private val compositeDisposable = CompositeDisposable()

    override fun setId(id: Int) {
        detailState.id.set(id)
    }

    override fun setName(name: String) {
        if (detailState.name.get() != name) {
            detailState.name.set(name)
        }
    }

    override fun setLastName(lastName: String) {
        if (detailState.lastName.get() != lastName) {
            detailState.lastName.set(lastName)
        }
    }

    override fun setAge(age: Int) {
        if (detailState.age.get() != age) {
            detailState.age.set(age)
        }
    }

    override fun setGender(gender: String) {
        if (detailState.gender.get() != gender) {
            detailState.gender.set(gender)
        }
    }

    override fun setAll(student: Student) {
        setId(student.id)
        setName(student.name)
        setLastName(student.lastName)
        setAge(student.age)
        setGender(student.gender)
    }

    override fun getAll(): Student {
        return Student(detailState.id.get() ?: 0,
            detailState.name.get().orEmpty(),
            detailState.lastName.get().orEmpty(),
            detailState.age.get() ?: 0,
            detailState.gender.get().orEmpty())
    }

    override fun getStudentById(id: Int) {
        compositeDisposable.add(
            detailDataSource.getStudentById(
                id,
                {
                    setAll(it)
                    view.initView(it)
                },
                { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
            )
        )
    }

    override fun buttonSaveClicked() {
        val student = getAll()
        compositeDisposable.add(
            detailDataSource.updateDataStudent(
                student,
                {
                    view.showSnackBar(resources.getString(R.string.success_message))
                    view.disabledViews()
                    view.initView(student)
                }, { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                })
        )
    }


    override fun onPositiveButtonClicked() {
        val student = getAll()
        compositeDisposable.add(
            detailDataSource.deleteStudentOfDataBase(
                student,
                {
                    view.navigateTo()
                    view.showSnackBar(resources.getString(R.string.delete_student))
                }, { error ->
                    view.showSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                })
        )
    }

    override fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    override fun buttonEditClicked() {
        view.enabledViews()
    }

    override fun buttonRemoveClicked() {
        view.showAlertDeleteDialog()
    }

}