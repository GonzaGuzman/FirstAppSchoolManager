package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.*
import com.zalo.firstAppMVP.network.models.ResponseNetwork
import com.zalo.firstAppMVP.network.models.School
import com.zalo.firstAppMVP.network.models.Schools
import com.zalo.firstAppMVP.registration.registrationDataSource.RegistrationDataSource
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationPresenter
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationState
import com.zalo.firstAppMVP.registration.registrationPresenter.RegistrationsView
import io.reactivex.rxjava3.disposables.Disposable
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@RunWith(MockitoJUnitRunner::class)
class RegistrationPresenterTest {
    @Mock
    private lateinit var registrationDataSource: RegistrationDataSource

    @Mock
    private lateinit var registrationView: RegistrationsView

    @Mock
    private lateinit var resources: Resources

    private val registrationState = RegistrationState()

    private lateinit var registrationPresenter: RegistrationPresenter

    private val out = ByteArrayOutputStream()
    private val originalOut = System.out

    @Mock
    private lateinit var mockDisposable: Disposable

    @Before
    fun setup() {
        registrationPresenter =
            RegistrationPresenter(registrationView,
                registrationDataSource,
                resources,
                registrationState)

        System.setOut(PrintStream(out))

    }

    @After
    fun restoreInitialStreams() {
        System.setOut(originalOut)
    }


    @Test
    fun `initView when sharedPreferences Is Data NotEmpty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(SCHOOL_NAME)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(SCHOOL_TYPE)

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView).initComponent(SCHOOL_NAME, SCHOOL_TYPE)
        verify(registrationView).viewDisabled()
    }

    //Controlar!!!
    @Test
    fun `initView when sharedPreferences Is Data Empty and getSchools success`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(EMPTY_STRING)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(EMPTY_STRING)
        getSchoolListSuccessfully()
        val mapSchools = LIST_SCHOOL_TEST.associateBy({ it.name }, { it.type })
        //WHEN
        registrationPresenter.initView()
        ARRAY_NAME_SCHOOL.sort()


        //THEN
        assertEquals(registrationState.listOfSchools.get(), mapSchools)
        verify(registrationView).listAdapter(ARRAY_NAME_SCHOOL)

    }

    @Test
    fun `initView when sharedPreferences Is Data Empty and getSchools fail`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(EMPTY_STRING)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(EMPTY_STRING)
        whenever(resources.getString(R.string.error_message)).thenReturn(FAIL)
        getSchoolListFailed()

        //WHEN
        registrationPresenter.initView()

        //THEN
        verify(registrationView).showSnackBar(String.format(FAIL, THIS_FAIL))

    }

    @Test
    fun `set schoolName`() {
        //GIVEN

        //WHEN
        registrationPresenter.setSchoolName(SCHOOL_NAME)

        //THEN
        verify(registrationDataSource).setSchoolNameInShared(SCHOOL_NAME)

    }

    @Test
    fun `set typeEducation`() {
        //GIVEN
        //WHEN
        registrationPresenter.setTypeEducation(SCHOOL_TYPE)

        //THEN
        verify(registrationDataSource).setTypeEducationInShared(SCHOOL_TYPE)
    }

    @Test
    fun `buttonContinueClicked is clicked and data Repository is empty`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(EMPTY_STRING)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(EMPTY_STRING)
        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(true)
    }


    @Test
    fun `buttonContinueClicked is clicked and data Repository is not empty and listOfSchool not contains Key`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(NEW_SCHOOL_NAME)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(SCHOOL_TYPE)
        val auxSchool = School(NEW_SCHOOL_NAME, SCHOOL_TYPE)
        val mutableMapSchools = mutableMapOf<String, String>()
        mutableMapSchools[SCHOOL_NAME] = SCHOOL_TYPE
        registrationState.listOfSchools.set(mutableMapSchools)
        postSchoolSuccessfully()

        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(false)
        assert(registrationState.listOfSchools.get()?.containsKey(auxSchool.name) == false)
        assertEquals(RESPONSE_POST, out.toString().trim())
        verify(registrationView).navigateTo()

    }

    @Test
    fun `buttonContinueClicked is clicked and data Repository is not empty and listOfSchool containsKey`() {
        //GIVEN
        whenever(registrationDataSource.getSchoolNameOfShared()).thenReturn(SCHOOL_NAME)
        whenever(registrationDataSource.getTypeEducationOfShared()).thenReturn(SCHOOL_TYPE)
        val auxSchool = School(SCHOOL_NAME, SCHOOL_TYPE)
        val mutableMapSchools = mutableMapOf<String, String>()
        mutableMapSchools[SCHOOL_NAME] = SCHOOL_TYPE
        registrationState.listOfSchools.set(mutableMapSchools)
        //WHEN
        registrationPresenter.buttonContinueClicked()

        //THEN
        verify(registrationView).setErrorName(false)
        assert(registrationState.listOfSchools.get()?.containsKey(auxSchool.name) == true)
        verify(registrationView).navigateTo()
    }

    @Test
    fun `buttonCloseSessionClicked pressed`() {
        //GIVEN

        //WHEN
        registrationPresenter.buttonCloseSessionClicked()

        //THEN
        verify(registrationView).showAlertCloseSession()

    }

    //VERIFICAR!!!
    @Test
    fun `getSchools successfully`() {
        //GIVEN
        getSchoolListSuccessfully()
        val mapSchools = LIST_SCHOOL_TEST.associateBy({ it.name }, { it.type })

        //WHEN
        registrationPresenter.getSchools()
        ARRAY_NAME_SCHOOL.sort()

        //THEN
        assertEquals(registrationState.listOfSchools.get(), mapSchools)
        verify(registrationView).listAdapter(ARRAY_NAME_SCHOOL)
    }

    @Test
    fun `getSchools failed`() {
        //GIVEN
        getSchoolListFailed()
        whenever(resources.getString(R.string.error_message)).thenReturn(RESPONSE_ERROR)

        //WHEN
        registrationPresenter.getSchools()

        //THEN
        verify(registrationView).showSnackBar(String.format(RESPONSE_ERROR, THIS_FAIL))
    }

    @Test
    fun `postSchools  success`() {
        //GIVEN
        postSchoolSuccessfully()
        val school = Mockito.mock(School::class.java)

        //WHEN
        registrationPresenter.postSchool(school)

        //THEN
        assertEquals(RESPONSE_POST, out.toString().trim())
    }

    @Test
    fun `postSchools fail`() {
        //GIVEN
        postSchoolUnsuccessfully()
        val school = Mockito.mock(School::class.java)

        //WHEN
        registrationPresenter.postSchool(school)

        //THEN
        assertEquals(ERROR_MESSAGE, out.toString().trim())
    }

    @Test
    fun `getArrayOfSchoolName return arrayList of String`() {
        //GIVEN
        val spyList = spy(arrayListOf<String>())
        val spyListSchools = spy(Schools(listOf(SCHOOL_1, SCHOOL_2, SCHOOL_3)))
        spyList.addAll(ARRAY_NAME_SCHOOL)
        //WHEN
        registrationPresenter.getArrayOfSchoolName(spyListSchools)
        spyList.sort()

        //THEN
        assertEquals(spyList.toList(), registrationState.listOfNamesSchools.get()?.toList())
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
        whenever(resources.getString(R.string.primaryEducation)).thenReturn(PRIMARY_TYPE)
        whenever(resources.getString(R.string.closed_session)).thenReturn(CLOSE_SESSION)
        val mapSchools = LIST_SCHOOL_TEST.associateBy({ it.name }, { it.type })
        getSchoolListSuccessfully()

        //WHEN
        registrationPresenter.onPositiveButtonClicked()

        //THEN
        verify(registrationView).viewEnabled()
        verify(registrationDataSource).wipe()
        verify(registrationDataSource).setTypeEducationInShared(PRIMARY_TYPE)
        verify(registrationView).showSnackBar(CLOSE_SESSION)
        assertEquals(registrationState.listOfSchools.get(), mapSchools)

    }


    private fun postSchoolSuccessfully() {
        val success = argumentCaptor<(ResponseNetwork) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val responseNetwork = Mockito.mock(ResponseNetwork::class.java)
        whenever(responseNetwork.msm).thenReturn(RESPONSE_POST)
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
        val responseThrowable = Mockito.mock(Throwable::class.java)

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
        val success = argumentCaptor<(Schools) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(schools.schools).thenReturn(LIST_SCHOOL_TEST)
        whenever(registrationDataSource.getSchoolsList(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(schools)
            mockDisposable
        }
    }

    private fun getSchoolListFailed() {
        val responseThrowable = Mockito.mock(Throwable::class.java)
        whenever(responseThrowable.message).thenReturn(THIS_FAIL)
        val success = argumentCaptor<(Schools) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(registrationDataSource.getSchoolsList(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(responseThrowable)
            mockDisposable
        }
    }

    companion object {
        const val EMPTY_STRING = ""
        const val FAIL = "ERROR: "
        const val ERROR_MESSAGE = "POST FAIL"
        const val THIS_FAIL = "NO SE OBTUVO LA LISTA"
        const val RESPONSE_ERROR = "ALGO SALIO MAL %s"
        const val SCHOOL_NAME = "SchoolName"
        const val SCHOOL_TYPE = "TypeEducation"
        const val NEW_SCHOOL_NAME = "NewSchoolName"
        const val RESPONSE_POST = "ESCUELA AGREGADA"
        const val PRIMARY_TYPE = "EDUCACIÓN PRIMARIA"
        const val CLOSE_SESSION = "SESION CERRADA"
        val ARRAY_NAME_SCHOOL = arrayListOf("schoolOne", "schoolTwo", "schoolThree")
        private val SCHOOL_1 = School("schoolOne", "PrimaryType")
        private val SCHOOL_2 = School("schoolTwo", "HighSchoolType")
        private val SCHOOL_3 = School("schoolThree", "BothOfThemType")
        val LIST_SCHOOL_TEST = listOf(SCHOOL_1, SCHOOL_2, SCHOOL_3)
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



