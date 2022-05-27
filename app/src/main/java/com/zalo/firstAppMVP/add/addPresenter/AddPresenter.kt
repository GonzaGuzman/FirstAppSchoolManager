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
    private val state: AddState,
) : AddActions {

    private val compositeDisposable = CompositeDisposable()


    override fun setName(name: String) {
        state.name.set(name)
    }

    override fun setLastName(lastName: String) {
        state.lastName.set(lastName)
    }

    override fun setAge(age: Int) {
        state.age.set(age)
    }

    override fun setGender(gender: String) {
        state.gender.set(gender)
    }

    override fun buttonAddClicked() {
        if (dataStudentIsNotEmpty()) {
            val student = Student(ZERO, state.name.get().orEmpty(), state.lastName.get().orEmpty(),
                state.age.get() ?: 0, state.gender.get() ?: INIT_GENDER)

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
        if (state.name.get().isNullOrEmpty()) {
            view.setErrorName(true)
        } else
            view.setErrorName(false)

        if (state.lastName.get().isNullOrEmpty()) {
            view.setErrorLastName(true)
        } else
            view.setErrorLastName(false)

        if (state.age.get() == ZERO) {
            view.setErrorAge(true)
        } else {
            view.setErrorAge(false)
        }
        return !(state.name.get().isNullOrEmpty() || state.lastName.get()
            .isNullOrEmpty() || (state.age.get() == ZERO))
    }

    override fun buttonCancelClicked() {
        view.navigateTo()
    }

    override fun reset() {
           state.name.set("")
           state.lastName.set("")
           state.age.set(0)
           state.gender.set(INIT_GENDER)
         }

}







