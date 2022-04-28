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
        if (registrationRepository.getRepositorySchoolName().isNotEmpty() && registrationRepository.getRepositorySchoolTypeEducation().isNotEmpty()) {
            view.initComponent(registrationRepository.getRepositorySchoolName(),
                registrationRepository.getRepositorySchoolTypeEducation())
            view.viewDisabled()
        }
    }

    fun setSchoolName(nameSchool: String) {
        registrationRepository.setRepositorySchoolName(nameSchool)
    }

    private fun setTypeEducation() {
        val currentTypeEducation = view.getTypeEducation()
        registrationRepository.setRepositorySchoolTypeEducation(currentTypeEducation)
    }

    override fun buttonContinueClicked() {
        if (registrationRepository.getRepositorySchoolName().isEmpty()) {
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
        registrationRepository.wipeRepository()
        view.showSuccessSnackBar(resources.getString(R.string.closed_session))
    }


}