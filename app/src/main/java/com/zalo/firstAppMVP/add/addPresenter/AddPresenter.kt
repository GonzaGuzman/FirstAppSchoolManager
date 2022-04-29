package com.zalo.firstAppMVP.add.addPresenter


import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.add.addDataSource.AddDataSource
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.CompositeDisposable


const val zero = 0

class AddPresenter(
    private val view: AddView,
    private val addDataSource: AddDataSource,
    private val resources: Resources,
) : AddActions {

    private val compositeDisposable = CompositeDisposable()

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun setName(name: String) {
        _student.value?.name = name
    }

    fun setLastName(lastName: String) {
        _student.value?.lastName = lastName
    }

    fun setAge(age: Int) {
        _student.value?.age = age

    }

    private fun setGender() {
        _student.value?.gender = view.getGender()
    }

    override fun buttonAddClicked() {
        if (_student.value?.name.isNullOrEmpty()) {
            view.setErrorName(true)
        } else
            view.setErrorName(false)

        if (_student.value?.lastName.isNullOrEmpty()) {
            view.setErrorLastName(true)
        } else
            view.setErrorLastName(false)

        if (_student.value?.age == zero) {
            view.setErrorAge(true)
        } else
            view.setErrorAge(false)

        if (view.validationForAdd()) {
            _student.value?.let {
                setGender()
                compositeDisposable.add(
                    addDataSource.insertNewStudent(
                        it,
                        {
                            view.showSnackBar(resources.getString(R.string.successfully_added))
                            view.resetView()
                        }, { error ->
                            view.showSnackBar(String.format(resources.getString(R.string.error_message),
                                error.message))
                        }
                    )
                )
            }
        } else
            view.showSnackBar(resources.getString(R.string.please_complete_all_fields))

    }

    override fun buttonCancelClicked() {
        view.navigateTo()
    }


    fun reset() {
        _student.value = Student(zero, "", "", zero, "")
    }

    init {
        reset()
    }


}







