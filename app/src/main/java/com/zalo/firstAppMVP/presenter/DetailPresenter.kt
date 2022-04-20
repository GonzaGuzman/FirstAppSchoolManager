package com.zalo.firstAppMVP.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zalo.firstAppMVP.data.StudentDataBase
import com.zalo.firstAppMVP.home.Student
import com.zalo.firstAppMVP.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailPresenter(private val view: DetailView, private val dataBaseResult: StudentDataBase) {

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun setName(name: String) {
        if(_student.value?.name != name) {
            _student.value?.name = name
        }
    }

    fun setLastName(lastName: String) {
        if(_student.value?.lastName != lastName) {
            _student.value?.lastName = lastName
        }
    }

    fun setAge(age: Int) {
        if(_student.value?.age != age) {
            _student.value?.age = age
        }
    }

    fun setGender(genderSelected: String) {
        if(_student.value?.gender != genderSelected) {
            _student.value?.gender = genderSelected
        }
    }

    fun updateStudent() {
       CompositeDisposable()
            .add(
                dataBaseResult.studentDao().update(_student.value!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                        view.initView(_student.value!!)
                    }
            )

    }



    fun getStudent(id: Int) {
        dataBaseResult.studentDao().getById(id)
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
        CompositeDisposable()
            .add(
                dataBaseResult.studentDao().delete(_student.value!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                    }
            )
    }

}