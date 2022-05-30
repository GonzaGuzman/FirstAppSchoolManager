package com.zalo.firstAppMVP.add.addPresenter


import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.add.addDataSource.AddDataSource
import com.zalo.firstAppMVP.add.addPresenter.AddState.Companion.INIT_GENDER
import com.zalo.firstAppMVP.add.addPresenter.AddState.Companion.ZERO
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.CompositeDisposable


class AddPresenter(
    private val view: AddView,
    private val addDataSource: AddDataSource,
    private val resources: Resources,
    private val addState: AddState,
) : AddActions {

    private val compositeDisposable = CompositeDisposable()


    override fun setName(name: String) {
        addState.name.set(name)
    }

    override fun setLastName(lastName: String) {
        addState.lastName.set(lastName)
    }

    override fun setAge(age: Int) {
        addState.age.set(age)
    }

    override fun setGender(gender: String) {
        addState.gender.set(gender)
    }

    override fun buttonAddClicked() {
        if (dataStudentIsNotEmpty()) {
            val student = Student(ZERO, addState.name.get().orEmpty(), addState.lastName.get().orEmpty(),
                addState.age.get() ?: 0, addState.gender.get() ?: INIT_GENDER)

            compositeDisposable.add(
                addDataSource.insertNewStudent(
                    student,
                    {
                        view.showSnackBar(resources.getString(R.string.successfully_added))
                        view.resetView()
                    }, { error ->
                        view.showSnackBar(String.format(resources.getString(R.string.error_message),
                            error.message))
                    }
                )
            )
        } else
            view.showSnackBar(resources.getString(R.string.please_complete_all_fields))
    }

    override fun dataStudentIsNotEmpty(): Boolean {
        if (addState.name.get().isNullOrEmpty()) {
            view.setErrorName(true)
        } else
            view.setErrorName(false)

        if (addState.lastName.get().isNullOrEmpty()) {
            view.setErrorLastName(true)
        } else
            view.setErrorLastName(false)

        if (addState.age.get() == ZERO) {
            view.setErrorAge(true)
        } else {
            view.setErrorAge(false)
        }
        return !(addState.name.get().isNullOrEmpty() || addState.lastName.get()
            .isNullOrEmpty() || (addState.age.get() == ZERO))
    }

    override fun buttonCancelClicked() {
        view.navigateTo()
    }

    override fun reset() {
           addState.name.set("")
           addState.lastName.set("")
           addState.age.set(0)
           addState.gender.set(INIT_GENDER)
         }

}







