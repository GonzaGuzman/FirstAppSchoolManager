package com.zalo.firstAppMVP.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddPresenter(
    private val view: AddView,
    private val detailRepository: DetailRepository,
) {
    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun setName(name: String) {
        if (name.isNullOrEmpty()) {
            view.setErrorName(true)
        } else
            view.setErrorName(false)
            _student.value?.name = name
        }


    fun setLastName(lastName: String) {
        if (lastName.isNullOrEmpty()) {
            view.setErrorLastName(true)
        }else
            view.setErrorLastName(false)
            _student.value?.lastName = lastName
        }

    fun setAge(age: Int) {
        if (age.toString().isNullOrEmpty()) {
            view.setErrorAge(true)
        }else
            view.setErrorAge(false)
            _student.value?.age = age
        }


    fun setGender(genderSelected: String) {
        _student.value?.gender = genderSelected

    }


    fun setStudent() {
        _student.value?.let {
            detailRepository.insert(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                }
        }?.let {
            CompositeDisposable()
                .add(
                    it
                )
        }
    }

        fun reset() {
            _student.value = Student(0,"","",0,"")
        }
    }







