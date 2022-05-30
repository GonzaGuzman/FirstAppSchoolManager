package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.detail.detailDataSource.DetailDataSourceImplements
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailState
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest {
    @Mock
    private lateinit var detailView: DetailView

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var detailDataSourceImplements: DetailDataSourceImplements

    @Mock
    private lateinit var mockDisposable: Disposable

    private val detailState = DetailState()

    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        detailPresenter =
            DetailPresenter(detailView, detailDataSourceImplements, resources, detailState)
    }

    @Test
    fun `setId in DetailState id`() {
        //GIVEN

        //WHEN
        detailPresenter.setId(ID)

        //THEN
        assertEquals(detailState.id.get(), ID)
    }

    @Test
    fun `setName in DetailState name`() {
        //GIVEN

        //WHEN
        detailPresenter.setName(NAME)

        //THEN
        assertEquals(detailState.name.get(), NAME)
    }

    @Test
    fun `setLastName in DetailState lastName`() {
        //GIVEN

        //WHEN
        detailPresenter.setLastName(LAST_NAME)

        //THEN
        assertEquals(detailState.lastName.get(), LAST_NAME)
    }

    @Test
    fun `setAge in DetailState age`() {
        //GIVEN

        //WHEN
        detailPresenter.setAge(AGE)

        //THEN
        assertEquals(detailState.age.get(), AGE)
    }

    @Test
    fun `setGender in DetailState gender`() {
        //GIVEN

        //WHEN
        detailPresenter.setGender(FEMALE_GENDER)

        //THEN
        assertEquals(detailState.gender.get(), FEMALE_GENDER)
    }

    @Test
    fun `setAll student data in DetailState `() {
        //GIVEN
        val student = Mockito.mock(Student::class.java)
        whenever(student.id).thenReturn(ID)
        whenever(student.name).thenReturn(NAME)
        whenever(student.lastName).thenReturn(LAST_NAME)
        whenever(student.age).thenReturn(AGE)
        whenever(student.gender).thenReturn(MALE_GENDER)

        //WHEN
        detailPresenter.setAll(student)

        //THEN
        assertEquals(detailState.id.get(), ID)
        assertEquals(detailState.name.get(), NAME)
        assertEquals(detailState.lastName.get(), LAST_NAME)
        assertEquals(detailState.age.get(), AGE)
        assertEquals(detailState.gender.get(), MALE_GENDER)
    }

    @Test
    fun `getAll data of DetailState in val Student`() {
        //GIVEN
        detailState.id.set(ID)
        detailState.name.set(NAME)
        detailState.lastName.set(LAST_NAME)
        detailState.age.set(AGE)
        detailState.gender.set(FEMALE_GENDER)

        //WHEN
        val student = detailPresenter.getAll()

        //THEN
        assertEquals(student.id, ID)
        assertEquals(student.name, NAME)
        assertEquals(student.lastName, LAST_NAME)
        assertEquals(student.age, AGE)
        assertEquals(student.gender, FEMALE_GENDER)
    }

    //aprender como verificar el objeto obtenido en success en las llamadas de otros metodos
    @Test
    fun `getById success`() {
        //GIVEN
        getStudentByIdSuccessfully()

        //WHEN
        detailPresenter.getStudentById(ID)

        //THEN
        assertEquals(detailState.id.get(), ID)
        assertEquals(detailState.name.get(), NAME)
        assertEquals(detailState.lastName.get(), LAST_NAME)
        assertEquals(detailState.age.get(), AGE)
        assertEquals(detailState.gender.get(), MALE_GENDER)
        verify(detailView).initView(any())
    }

    @Test
    fun `getById fail`() {
        //GIVEN
        getStudentByIdUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(ERROR_MESSAGE)
        //WHEN
        detailPresenter.getStudentById(ID)

        //THEN
        verify(detailView).showSnackBar(String.format(ERROR_MESSAGE, THIS_FAIL))
    }


    @Test
    fun `buttonSaveClicked pressed and updateStudent is successfully`() {
        //GIVEN
        updateDataStudentSuccessfully()
        whenever(resources.getString(R.string.success_message)).thenReturn(SUCCESS_MESSAGE)
        //WHEN

        detailPresenter.buttonSaveClicked()

        //THEN
        verify(detailView).showSnackBar(SUCCESS_MESSAGE)
        verify(detailView).disabledViews()
        verify(detailView).initView(any())
    }


    @Test
    fun `buttonSaveClicked pressed and updateStudent is unsuccessfully`() {
        //GIVEN
        updateDataStudentUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(ERROR_MESSAGE)

        //WHEN
        detailPresenter.buttonSaveClicked()

        //THEN
        verify(detailView).showSnackBar(String.format(ERROR_MESSAGE, THIS_FAIL))
    }


    @Test
    fun `onPositiveButtonClicked pressed and deleteStudent successfully`() {
        //GIVEN
        deleteStudentSuccessfully()
        whenever(resources.getString(R.string.delete_student)).thenReturn(DELETE_STUDENT)

        //WHEN
        detailPresenter.onPositiveButtonClicked()

        //THEN
        verify(detailView).navigateTo()
        verify(detailView).showSnackBar(DELETE_STUDENT)
    }


    @Test
    fun `onPositiveButtonClicked pressed and deleteStudent unsuccessfully`() {
        //GIVEN
        deleteStudentUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(ERROR_MESSAGE)

        //WHEN
        detailPresenter.onPositiveButtonClicked()

        //THEN
        verify(detailView).showSnackBar(String.format(ERROR_MESSAGE, THIS_FAIL))
    }

    @Test
    fun `onNegativeButtonClicked pressed`() {
        //GIVEN

        //WHEN
        detailPresenter.onNegativeButtonClicked()

        //THEN
        verify(detailView).dialogDismiss()
    }

    @Test
    fun `buttonEditClicked pressed`() {
        //GIVEN

        //WHEN
        detailPresenter.buttonEditClicked()

        //THEN
        verify(detailView).enabledViews()
    }

    @Test
    fun `buttonRemoveClicked pressed`() {
        //GIVEN

        //WHEN
        detailPresenter.buttonRemoveClicked()

        //THEN
        verify(detailView).showAlertDeleteDialog()
    }

    private fun getStudentByIdSuccessfully() {
        val student = Mockito.mock(Student::class.java)
        whenever(student.id).thenReturn(ID)
        whenever(student.name).thenReturn(NAME)
        whenever(student.lastName).thenReturn(LAST_NAME)
        whenever(student.age).thenReturn(AGE)
        whenever(student.gender).thenReturn(MALE_GENDER)

        val successCaptor = argumentCaptor<(Student) -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSourceImplements.getStudentById(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            successCaptor.firstValue.invoke(student)
            mockDisposable
        }
    }


    private fun getStudentByIdUnsuccessfully() {
        val responseError = Mockito.mock(Throwable::class.java)
        val successCaptor = argumentCaptor<(Student) -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()
        whenever(responseError.message).thenReturn(THIS_FAIL)
        whenever(
            detailDataSourceImplements.getStudentById(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            errorCaptor.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    private fun updateDataStudentSuccessfully() {
        val successCaptor = argumentCaptor<() -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSourceImplements.updateDataStudent(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            successCaptor.firstValue.invoke()
            mockDisposable
        }

    }

    private fun updateDataStudentUnsuccessfully() {
        val responseError = Mockito.mock(Throwable::class.java)
        val successCaptor = argumentCaptor<() -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()
        whenever(responseError.message).thenReturn(THIS_FAIL)
        whenever(
            detailDataSourceImplements.updateDataStudent(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            errorCaptor.firstValue.invoke(responseError)
            mockDisposable
        }
    }

    private fun deleteStudentSuccessfully() {
        val successCaptor = argumentCaptor<() -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSourceImplements.deleteStudentOfDataBase(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            successCaptor.firstValue.invoke()
            mockDisposable
        }
    }

    private fun deleteStudentUnsuccessfully() {
        val responseError = Mockito.mock(Throwable::class.java)
        val successCaptor = argumentCaptor<() -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()
        whenever(responseError.message).thenReturn(THIS_FAIL)
        whenever(
            detailDataSourceImplements.deleteStudentOfDataBase(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            errorCaptor.firstValue.invoke(responseError)
            mockDisposable
        }
    }


    companion object {
        const val ID = 21
        const val NAME = "Android"
        const val LAST_NAME = "Studio"
        const val AGE = 10
        const val FEMALE_GENDER = "Femenino"
        const val MALE_GENDER = "Masculino"
        const val THIS_FAIL = "ERROR"
        const val ERROR_MESSAGE = "ALGO SALIO MAL %s"
        const val SUCCESS_MESSAGE = "ACTUALIZACION EXITOSA"
        const val DELETE_STUDENT = "Alumno Eliminado"
    }
}