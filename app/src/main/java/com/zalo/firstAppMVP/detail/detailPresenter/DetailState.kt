package com.zalo.firstAppMVP.detail.detailPresenter

import androidx.databinding.ObservableField

class DetailState {
    val id: ObservableField<Int> = ObservableField<Int>(ZERO)
    val name: ObservableField<String?> = ObservableField<String?>()
    val lastName: ObservableField<String?> = ObservableField<String?>()
    val age: ObservableField<Int> = ObservableField<Int>(ZERO)
    val gender: ObservableField<String> = ObservableField<String>(INIT_GENDER)


    companion object{
        const val ZERO = 0
        const val INIT_GENDER = "Masculino"
    }
}
