package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import com.zalo.firstAppMVP.registration.registrationRepository.RegistrationRepository
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

/*
@RunWith(MockitoJUnitRunner::class)
class RegistrationPresenterTest {
    @Mock
    private lateinit var registrationRepository: RegistrationRepository

    @Mock
    private lateinit var registrationView: RegistrationsView

    @Mock
    private lateinit var resources: Resources

    private lateinit var registrationPresenter: RegistrationPresenter
*/

/*    @Before
    fun setup() {
        registrationPresenter =
            RegistrationPresenter(registrationView, registrationRepository, resources)
    }

    //MethodName_StateUnderTest_ExpectedBehavior
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
        verify(registrationView,
            never()).initComponent(registrationRepository.getRepositorySchoolName(),
            registrationRepository.getRepositorySchoolTypeEducation())

    }


    @Test
    fun `set name on RepositorySchoolName`() {
        //GIVEN

        //WHEN
        registrationPresenter.setSchoolName("SCHOOLTEST")

        //THEN
        verify(registrationRepository).setRepositorySchoolName("SCHOOLTEST")
    }

    @Test
    fun `clicked button continue with not empty repository`() {
        //GIVEN
        whenever(registrationRepository.getRepositorySchoolName()).thenReturn("gonza")

        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(false)
        verify(registrationView).navigateTo()
        verify(registrationView, never()).setErrorName(true)
    }

    @Test
    fun `clicked button continue with empty repository`() {
        //GIVEN
        whenever(registrationRepository.getRepositorySchoolName()).thenReturn("")

        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(true)
        verify(registrationView, never()).setErrorName(false)
        verify(registrationView, never()).navigateTo()
    }

    @Test
    fun `clicked button closeSession`() {
        //GIVEN

        //WHEN
        registrationPresenter.buttonCloseSessionClicked()
        //THEN
        verify(registrationView).showAlertCloseSession()
    }

    @Test
    fun `clicked negative button `() {
        //GIVEN

        //WHEN
        registrationPresenter.onNegativeButtonClicked()

        //THEN
        verify(registrationView).dialogDismiss()
    }

    @Test
    fun `clicked positive button`() {
        // GIVEN
        whenever(resources.getString(R.string.closed_session)).thenReturn("session Cerrada")

        //WHEN
        registrationPresenter.onPositiveButtonClicked()

        //THEN
        verify(registrationView).viewEnabled()
        verify(registrationRepository).wipeRepository()
        verify(registrationView).showSnackBar(resources.getString(R.string.closed_session))


    }
}*/


