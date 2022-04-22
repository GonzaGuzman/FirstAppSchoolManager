package com.zalo.firstAppMVP.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.repository.StudentRepository
import com.zalo.firstAppMVP.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailPresenter(
    private val view: DetailView,
    private val studentRepository: StudentRepository,
) {

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

    fun setGender(genderSelected: String) {
        if (_student.value?.gender != genderSelected) {
            _student.value?.gender = genderSelected
        }
    }


    fun updateStudent() {
        _student.value?.let {
            studentRepository.update(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                    _student.value?.let { view.initView(it) }
                }
        }?.let {
            CompositeDisposable()
                .add(
                    it
                )
        }

    }


    fun getStudent(id: Int) {
        studentRepository.getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _student.value = it
                view.initView(it)
            }, {
                println(it.message)
            })
    }


    fun deleteStudent() {
        _student.value?.let {
            studentRepository.delete(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                    view.navigateTo()
                }
        }?.let {
            CompositeDisposable()
                .add(
                    it
                )
        }
    }

}