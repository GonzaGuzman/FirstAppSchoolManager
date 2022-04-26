package com.zalo.firstAppMVP.detail.detailPresenter

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailPresenter(
    private val view: DetailView,
    private val detailRepository: DetailRepository,
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
            detailRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _student.value = it
                    view.initView(it)
                }, { error ->
                    view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
                )
        )
    }

    override fun buttonSaveClicked() {
        _student.value?.let {
            compositeDisposable.add(
                detailRepository.update(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.showSuccessSnackBar(resources.getString(R.string.success_message))
                        view.disabledViews()
                        _student.value?.let { updatedStudent -> view.initView(updatedStudent) }
                    }, { error ->
                        view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
                            error.message))
                    })
            )
        }

    }

    fun onPositiveButtonClicked() {
        _student.value?.let {
            compositeDisposable.add(
                detailRepository.delete(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.navigateTo()
                        view.showSuccessSnackBar(resources.getString(R.string.delete_student))
                    },
                        { error ->
                            view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
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