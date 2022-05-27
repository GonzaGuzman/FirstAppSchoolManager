package com.zalo.firstAppMVP.registration.registrationPresenter

import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools

interface RegistrationActions {
    fun buttonContinueClicked()
    fun buttonCloseSessionClicked()
    fun getSchools()
    fun initView()
    fun setSchoolName(nameSchool: String)
    fun setTypeEducation(currentTypeEducation: String)
    fun postSchool(school: School)
    fun getArrayOfSchoolName(list: Schools)
    fun validateTypeEducation(name: String)
    fun onNegativeButtonClicked()
    fun onPositiveButtonClicked()
}
