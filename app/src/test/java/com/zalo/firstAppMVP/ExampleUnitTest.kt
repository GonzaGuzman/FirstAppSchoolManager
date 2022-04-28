package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationActions
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrationFragmentTest {
    @Mock
    private lateinit var registrationRepository: RegistrationRepository

    @Mock
    private lateinit var registrationView: RegistrationsView

    @Mock
    private lateinit var resources: Resources

    private lateinit var registrationPresenter: RegistrationPresenter

    @Before
    fun setup() {
        registrationPresenter =
            RegistrationPresenter(registrationView, registrationRepository, resources)
        whenever(resources.getString(R.string.primaryEducation)).thenReturn("EDUCACIÓN PRIMARIA")

    }

    @Test
    fun `onRepository in not empty show Repository info`() {
        //GIVEN
        whenever(registrationRepository.getRepositorySchoolName()).thenReturn("SchoolNameTest")
        whenever(registrationRepository.getRepositorySchoolTypeEducation()).thenReturn("EDUCACIÓN PRIMARIA")

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView).initComponent(registrationRepository.getRepositorySchoolName(),
            registrationRepository.getRepositorySchoolTypeEducation())

    }


    @Test
    fun `onRepository in empty show Repository info`() {
        //GIVEN
        whenever(registrationRepository.getRepositorySchoolName()).thenReturn("")
        whenever(registrationRepository.getRepositorySchoolTypeEducation()).thenReturn("")

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView, never()).initComponent(registrationRepository.getRepositorySchoolName(),
            registrationRepository.getRepositorySchoolTypeEducation())

    }


    @Test
    fun `set name on RepositorySchoolName`() {
        //GIVEN

        //WHEN
        registrationPresenter.setSchoolName("pochi")

        //THEN
        verify(registrationRepository).setRepositorySchoolName("gonza")
    }


}