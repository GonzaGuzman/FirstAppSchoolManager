package com.zalo.firstAppMVP.registration.registrationPresenter

import androidx.databinding.ObservableField

class RegistrationState {

    val listOfSchools: ObservableField<MutableMap<String, String>?> =
        ObservableField<MutableMap<String, String>?>()
    val listOfNamesSchools: ObservableField<ArrayList<String>?> =
        ObservableField<ArrayList<String>?>()
}