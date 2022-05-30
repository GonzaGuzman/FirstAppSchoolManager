package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.add.addDataSource.AddDataSource
import com.zalo.firstAppMVP.add.addPresenter.AddPresenter
import com.zalo.firstAppMVP.add.addPresenter.AddState
import com.zalo.firstAppMVP.add.addPresenter.AddState.Companion.INIT_GENDER
import com.zalo.firstAppMVP.add.addPresenter.AddView
import io.reactivex.rxjava3.disposables.Disposable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTest {

    @Mock
    private lateinit var addDataSource: AddDataSource

    @Mock
    private lateinit var addView: AddView

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var mockDisposable: Disposable

    private val addState = AddState()

    private lateinit var addPresenter: AddPresenter

    @Before
    fun setup() {
        addPresenter = AddPresenter(addView, addDataSource, resources, addState)
    }

    @Test
    fun `setName in AddState name`(){
        //GIVEN


        //WHEN
        addPresenter.setName(NAME)

        //THEN
        assertEquals(addState.name.get(), NAME)

    }

    @Test
    fun `setLastName in AddState lastName`(){
        //GIVEN


        //WHEN
        addPresenter.setLastName(LAST_NAME)

        //THEN
        assertEquals(addState.lastName.get(), LAST_NAME)

    }
    @Test
    fun `setAge in AddState age`(){
        //GIVEN


        //WHEN
        addPresenter.setAge(AGE)

        //THEN
        assertEquals(addState.age.get(), AGE)

    }
    @Test
    fun `setGender in state gender`(){
        //GIVEN


        //WHEN
        addPresenter.setGender(FEMALE_GENDER)

        //THEN
        assertEquals(addState.gender.get(), FEMALE_GENDER)

    }

    @Test
    fun `when buttonAddClicked is pressed and dataStudent is empty`() {
        //GIVEN
        whenever(resources.getString(R.string.please_complete_all_fields)).thenReturn(COMPLETE)

        //WHEN

        addPresenter.buttonAddClicked()

        //THEN
        verify(addView).setErrorName(true)
        verify(addView).setErrorLastName(true)
        verify(addView).setErrorAge(true)
        verify(addView).showSnackBar(COMPLETE)

    }


    @Test
    fun `when buttonAddClicked is pressed and dataStudent is not empty`() {
        //GIVEN
        addPresenter.setName(NAME)
        addPresenter.setLastName(LAST_NAME)
        addPresenter.setAge(AGE)
        insertStudentSuccessfully()
        whenever(resources.getString(R.string.successfully_added)).thenReturn(ADDED)
        //WHEN

        addPresenter.buttonAddClicked()

        //THEN
        verify(addView).setErrorName(false)
        verify(addView).setErrorLastName(false)
        verify(addView).setErrorAge(false)
        verify(addView).resetView()
        verify(addView).showSnackBar(ADDED)


    }

    @Test
    fun `when buttonAddClicked is pressed and dataStudent is not empty and DataSource Fail`() {
        //GIVEN
        addPresenter.setName(NAME)
        addPresenter.setLastName(LAST_NAME)
        addPresenter.setAge(AGE)
        insertStudentUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(FAIL)
        //WHEN

        addPresenter.buttonAddClicked()

        //THEN
        verify(addView).setErrorName(false)
        verify(addView).setErrorLastName(false)
        verify(addView).setErrorAge(false)
        verify(addView).showSnackBar(String.format(FAIL, THIS_FAILED))
    }

    @Test
    fun `buttonCancelClicked is pressed`() {
        //GIVEN

        //WHEN
        addPresenter.buttonCancelClicked()

        //THEN
        verify(addView).navigateTo()

    }

    @Test
    fun `when Reset is call`() {
        //GIVEN
        addPresenter.setName(NAME)
        addPresenter.setLastName(LAST_NAME)
        addPresenter.setAge(AGE)
        addPresenter.setGender(FEMALE_GENDER)
        //WHEN
        addPresenter.reset()

        //THEN
        assertEquals(addState.name.get(), EMPTY_STRING)
        assertEquals(addState.lastName.get(), EMPTY_STRING)
        assertEquals(addState.age.get(), INT_ZERO)
        assertEquals(addState.gender.get(), INIT_GENDER)


    }

    private fun insertStudentSuccessfully() {
        val success = argumentCaptor<() -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(addDataSource.insertNewStudent(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke()
            mockDisposable
        }
    }

    private fun insertStudentUnsuccessfully() {
        val responseThrowable = Mockito.mock(Throwable::class.java)
        whenever(responseThrowable.message).thenReturn(THIS_FAILED)
        val success = argumentCaptor<() -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(addDataSource.insertNewStudent(
            any(),
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(responseThrowable)
            mockDisposable
        }
    }

    companion object {
        const val COMPLETE = "POR FAVOR COMPLETA TODOS LOS CAMPOS"
        const val ADDED = "Agregado exitosamente"
        const val FAIL = "ALGO SALIO MAL %s"
        const val THIS_FAILED = "FALLA, NO SE PUDO AGREGAR"
        const val NAME = "Nombre Student"
        const val LAST_NAME = "Apellido Student"
        const val AGE = 2
        const val FEMALE_GENDER = "Femenino"
        const val EMPTY_STRING = ""
        const val INT_ZERO = 0
    }


}


