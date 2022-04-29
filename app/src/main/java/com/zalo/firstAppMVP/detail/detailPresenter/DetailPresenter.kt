package com.zalo.firstAppMVP.detail.detailPresenter

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun setName(name: String) {
        if (_student.value?.name != name) {
            _student.value?.name = name
        }
    }

    fun setLastName(lastName: String) {
        if (_student.value?.lastName != lastName) {
            _student.value?.lastName = lastName
        }
    }

    fun setAge(age: Int) {
        if (_student.value?.age != age) {
            _student.value?.age = age
        }
    }

    override fun setGender(gender: String) {
        if (_student.value?.gender != gender) {
            _student.value?.gender = gender
        }
    }


    fun getStudentById(id: Int) {
        compositeDisposable.add(
            detailDataSource.getStudentById(
                id,
                {
                    _student.value = it
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
        _student.value?.let {
            compositeDisposable.add(
                detailDataSource.updateDataOfStudent(
                    it,
                    {
                        view.showSnackBar(resources.getString(R.string.success_message))
                        view.disabledViews()
                        _student.value?.let { updatedStudent -> view.initView(updatedStudent) }
                    }, { error ->
                        view.showSnackBar(String.format(resources.getString(R.string.error_message),
                            error.message))
                    })
            )
        }

    }

    fun onPositiveButtonClicked() {
        _student.value?.let {
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


    fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    override fun buttonEditClicked() {
        view.enabledViews()
    }

    override fun buttonRemoveClicked() {
        view.showAlertDeleteDialog()
    }

}