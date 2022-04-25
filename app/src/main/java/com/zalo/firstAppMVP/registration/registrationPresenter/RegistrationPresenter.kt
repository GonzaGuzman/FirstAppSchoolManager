package com.zalo.firstAppMVP.registration.registrationPresenter

import android.content.res.Resources
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository

class RegistrationPresenter(
    private val view: RegistrationsView,
    private val registrationRepository: RegistrationRepository,
    private val resources: Resources,
) : RegistrationActions {

    fun initView() {
        if (registrationRepository.schoolName.isNotEmpty() && registrationRepository.typeEducation.isNotEmpty()) {
            view.initComponent(registrationRepository.schoolName,
                registrationRepository.typeEducation)
            view.viewDisabled()
        }
    }

    fun setSchoolName(nameSchool: String) {
        registrationRepository.schoolName = nameSchool
    }

    private fun setTypeEducation() {
        registrationRepository.typeEducation = view.getTypeEducation()
    }

    override fun buttonContinueClicked() {
        if (registrationRepository.schoolName.isEmpty()) {
            view.setErrorName(true)
        } else {
            view.setErrorName(false)
            setTypeEducation()
            view.navigateTo()
        }
    }

    override fun buttonCloseSessionClicked() {
        view.showAlertCloseSession()
    }

    fun onNegativeButtonClicked() {
        view.dialogDismiss()
    }

    fun onPositiveButtonClicked() {
        view.viewEnabled()
        registrationRepository.wipe()
        view.showSuccessSnackBar(resources.getString(R.string.closed_session))
    }


}