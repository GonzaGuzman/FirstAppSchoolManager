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
) : DetailActions {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var student: Student

    override fun setName(name: String) {
        if (student.name != name) {
            student.name = name
        }
    }

    override fun setLastName(lastName: String) {
        if (student.lastName != lastName) {
            student.lastName = lastName
        }
    }

    override fun setAge(age: Int) {
        if (student.age != age) {
            student.age = age
        }
    }

    override fun setGender(gender: String) {
        if (student.gender != gender) {
            student.gender = gender
        }
    }


    override fun getStudentById(id: Int) {
        compositeDisposable.add(
            detailDataSource.getStudentById(
                id,
                {
                    student = it
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
        student.let {
            compositeDisposable.add(
                detailDataSource.updateDataOfStudent(
                    it,
                    {
                        view.showSnackBar(resources.getString(R.string.success_message))
                        view.disabledViews()
                        student.let { updatedStudent -> view.initView(updatedStudent) }
                    }, { error ->
                        view.showSnackBar(String.format(resources.getString(R.string.error_message),
                            error.message))
                    })
            )
        }

    }

   override fun onPositiveButtonClicked() {
        student.let {
            compositeDisposable.add(
                detailDataSource.deleteStudentOfDataBase(
                    it,
                    {
                        view.navigateTo()
                        view.showSnackBar(resources.getString(R.string.delete_student))
                    }, { error ->
                        view.showSnackBar(String.format(resources.getString(R.string.error_message),
                            error.message))
                    })
            )
        }
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