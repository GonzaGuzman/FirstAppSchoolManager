package com.zalo.firstAppMVP.add.addPresenter


import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.add.addRepository.AddRepository
import com.zalo.firstAppMVP.homeActivity.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

const val zero = 0

class AddPresenter(
    private val view: AddView,
    private val addRepository: AddRepository,
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
                    addRepository.insert(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.showSuccessSnackBar(resources.getString(R.string.successfully_added))
                            view.resetView()
                        }, { error ->
                            view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
                                error.message))
                        })
                )
            }
        } else
            view.showErrorSnackBar(resources.getString(R.string.please_complete_all_fields))

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







