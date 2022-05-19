package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.*
import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationDataSource.RegistrationDataSource
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import io.reactivex.rxjava3.disposables.Disposable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrationPresenterTest {
    @Mock
    private lateinit var registrationDataSource: RegistrationDataSource

    @Mock
    private lateinit var registrationView: RegistrationsView

    @Mock
    private lateinit var resources: Resources

    @InjectMocks
    private lateinit var registrationPresenter: RegistrationPresenter

    @Mock
    private lateinit var mockDisposable: Disposable

    @Before
    fun setup() {
        registrationPresenter =
            RegistrationPresenter(registrationView, registrationDataSource, resources)

    }

    @Test
    fun `initView  when sharedPreferences Is NotEmpty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn("SchoolName")
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn("Type Education")

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView).initComponent(registrationDataSource.getSchoolNameOfShared(),
            registrationDataSource.getTypeEducationOfShared())
    }


    @Test
    fun `initView when sharedPreferences Is Empty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn("")
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn("")
        getSchoolListSuccessfully()

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView,
            never()).initComponent(registrationDataSource.getSchoolNameOfShared(),
            registrationDataSource.getTypeEducationOfShared())
        assertEquals(registrationPresenter.getSchools(), Unit)

    }

    @Test
    fun `set schoolName`() {
        //GIVEN
        val name = "SchoolName"
        //WHEN
        registrationPresenter.setSchoolName(name)

        //THEN
        verify(registrationDataSource).setSchoolNameInShared(name)
    }

    @Test
    fun `set typeEducation`() {
        //GIVEN
        val type = "TypeEducation"
        //WHEN
        registrationPresenter.setTypeEducation(type)

        //THEN
        verify(registrationDataSource).setTypeEducationInShared(type)
    }

    @Test
    fun `buttonContinueClicked is clicked when Repository is not empty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn("SchoolName")
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn("TypeEducation")
        postSchoolSuccessfully()
        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(false)
        verify(registrationView).navigateTo()
    }

    @Test
    fun `buttonContinueClicked is clicked when Repository is empty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn("")
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn("")
        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(true)
    }

    @Test
    fun `buttonCloseSessionClicked pressed`() {
        //GIVEN

        //WHEN
        registrationPresenter.buttonCloseSessionClicked()

        //THEN
        verify(registrationView).showAlertCloseSession()

    }

    @Test
    fun `getSchools successfully`() {
        //GIVEN
        getSchoolListSuccessfully()

        //WHEN
        registrationPresenter.getSchools()

        //THEN
        verify(registrationView).listAdapter(any())
    }

    @Test
    fun `getSchools failed`() {
        //GIVEN
        getSchoolListFailed()
        whenever(resources.getString(R.string.error_message)).thenReturn("Failure message")

        //WHEN
        registrationPresenter.getSchools()

        //THEN
        verify(registrationView).showSnackBar(any())
    }

    /*@Test
    fun `postSchool success`() {
        //GIVEN
        val school = Mockito.mock(School::class.java)
        postSchoolSuccessfully()
        val responseNetwork = Mockito.mock(ResponseNetwork::class.java).apply {
            whenever(this.msm).thenReturn("ok")
        }
        //WHEN
        registrationPresenter.postSchool(school)

        //THEN
        assertEquals("OK", responseNetwork.msm.uppercase())
    }


    @Test
    fun `postSchool failed`() {
        //GIVEN
        val school = Mockito.mock(School::class.java)
        postSchoolSuccessfully()
       *//* val throwable = Mockito.mock(Throwable::class.java).apply {
            whenever(this.message).thenReturn("Failed")
        }*//*
        //WHEN
        registrationPresenter.postSchool(school)

        //THEN
        assertEquals("FAILED",this)
    }
*/
    @Test
    fun `getArrayOfSchoolName return arrayList of String`() {
        //GIVEN
        val schools = Mockito.mock(Schools::class.java)
        val spyList = spy(arrayListOf<String>())
        val spyListSchools = spy(schools)
        spyList.addAll(arrayListOf("schoolOne", "schoolTwo", "schoolThree"))
        val school1 = School("schoolOne", "PrimaryType")
        val school2 = School("schoolTwo", "HighSchoolType")
        val school3 = School("schoolThree", "BothOfThemType")
        spyListSchools.apply {
            whenever(this.schools).thenReturn(listOf(school1,
                school2,
                school3))
        }

        //WHEN
        val expected = registrationPresenter.getArrayOfSchoolName(spyListSchools)
        spyList.sort()
        expected.sort()

        //THEN
        assertEquals(spyList.toList(), expected.toList())
    }


    @Test
    fun `onNegativeButtonClicked pressed`() {
        //GIVEN

        //WHEN
        registrationPresenter.onNegativeButtonClicked()

        //THEN
        verify(registrationView).dialogDismiss()
    }


    @Test
    fun `onPositiveButtonClicked pressed`() {
        //GIVEN
        whenever(resources.getString(R.string.primaryEducation)).thenReturn("typeEducation")
        whenever(resources.getString(R.string.closed_session)).thenReturn("closed Message")
        getSchoolListSuccessfully()
        //WHEN
        registrationPresenter.onPositiveButtonClicked()

        //THEN
        verify(registrationView).viewEnabled()
        verify(registrationDataSource).wipe()
        verify(registrationView).showSnackBar(anyString())

    }

    private fun postSchoolSuccessfully() {
        val success = argumentCaptor<(ResponseNetwork) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseNetwork = Mockito.mock(ResponseNetwork::class.java).apply {
            whenever(this.msm).thenReturn("ok")
        }
        whenever(registrationDataSource.postSchool(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(responseNetwork)
            mockDisposable
        }
    }

    private fun postSchoolUnsuccessfully() {
        val success = argumentCaptor<(ResponseNetwork) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseThrowable = Mockito.mock(Throwable::class.java).apply {
           whenever(this.message).thenReturn("Failed")
        }
        whenever(registrationDataSource.postSchool(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(responseThrowable)
            mockDisposable
        }
    }


    private fun getSchoolListSuccessfully() {
        val schools = Mockito.mock(Schools::class.java)
        // val scl = spy<Schools>().schools
        val success = argumentCaptor<(Schools) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(registrationDataSource.getSchoolsList(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(schools)
            mockDisposable
        }
    }

    private fun getSchoolListFailed() {
        val fail = Mockito.mock(Throwable::class.java)
        val success = argumentCaptor<(Schools) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(registrationDataSource.getSchoolsList(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(fail)
            mockDisposable
        }
    }
}


/*
 @Test
    fun ``() {
        //GIVEN

        //WHEN

        //THEN


    }
*/



