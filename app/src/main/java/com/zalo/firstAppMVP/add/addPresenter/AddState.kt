package com.zalo.firstAppMVP.add.addPresenter

import androidx.databinding.ObservableField


class AddState {

    val name: ObservableField<String?> = ObservableField<String?>()
    val lastName: ObservableField<String?> = ObservableField<String?>()
    val age: ObservableField<Int> = ObservableField<Int>(ZERO)
    val gender: ObservableField<String> = ObservableField<String>(INIT_GENDER)


    companion object{
        const val ZERO = 0
        const val INIT_GENDER = "Masculino"
    }
}