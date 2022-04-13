package com.zalo.myrecyclerview.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class StudentViewModel : ViewModel() {

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> = _age

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender


    fun setName(name: String) {
        _name.value = name
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun setAge(age: Int) {
        _age.value = age
    }

    fun setGender(genderSelected: String) {
        _gender.value = genderSelected
    }


    fun getStudent(id: Int) {
        MyApplication.dataBase.studentDao().getById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setName(it.name)
                setLastName(it.lastName)
                setAge(it.age)
                setGender(it.gender)
                _student.value = it
            }, {
                println(it.message)
            })
    }


    fun setStudent(student: Student) {
        CompositeDisposable()
            .add(
                MyApplication.dataBase.studentDao().insert(student)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                    }
            )

    }

    fun updateStudent() {
        if (_student.value?.name != _name.value) {
            _student.value?.name = _name.value.toString()
        }
        if (_student.value?.lastName != _lastName.value) {
            _student.value?.lastName = _lastName.value.toString()
        }
        if (_student.value?.age != _age.value) {
            _student.value?.age = _age.value?.toInt() ?: 0
        }
        if (_student.value?.gender != _gender.value) {
            _student.value?.gender = _gender.value.toString()
        }

        CompositeDisposable()
            .add(
                MyApplication.dataBase.studentDao().update(_student.value!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                    }
            )

    }

    fun deleteStudent() {
        CompositeDisposable()
            .add(
                MyApplication.dataBase.studentDao().delete(_student.value!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeAndLogErrors {
                    }
            )
    }

    fun reset() {
        _student.value = null
    }
}
