package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.home.homeDataSource.HomeDataSource
import com.zalo.firstAppMVP.home.homePresenter.HomePresenter
import com.zalo.firstAppMVP.home.homePresenter.HomeView
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
class HomePresenterTest {
    @Mock
    private lateinit var homeDataSource: HomeDataSource

    @Mock
    private lateinit var homeView: HomeView

    @Mock
    private lateinit var resources: Resources

    private lateinit var homePresenter: HomePresenter

    @Mock
    private lateinit var mockDisposable: Disposable

    @Before
    fun setup() {
        homePresenter = HomePresenter(homeView, homeDataSource, resources)
    }

    @Test
    fun `when buttonAddClicked is pressed`() {
        //GIVEN

        //WHEN
        homePresenter.buttonAddClicked()

        //THEN
        verify(homeView).navigateTo()


    }

    @Test
    fun `when initComponent is successfully`() {
        //GIVEN
        getListOfStudentSuccessfully()
        val listOfStudent = Mockito.mock(listOf<Student>()::class.java)
        //WHEN
        homePresenter.initComponent()

        //THEN
        assertEquals(homeView.loadRecycler(listOf()), homeView.loadRecycler(listOfStudent))
    }


    @Test
    fun `when initComponent is unsuccessfully`() {
        //GIVEN
        getListOfStudentUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn(FAIL)

        //WHEN
        homePresenter.initComponent()

        //THEN
        verify(homeView).showSnackBar(String.format(FAIL, THIS_FAILED))
    }

    @Test
    fun `when initSchoolDate is call`() {
        //GIVEN
        whenever(homeDataSource.getSchoolName()).thenReturn(SCHOOL_NAME)
        whenever(homeDataSource.getSchoolTypeEducation()).thenReturn(SCHOOL_TYPE)

        //WHEN
        homePresenter.initSchoolDate()

        //THEN
        verify(homeView).showSchoolName(SCHOOL_NAME)
        verify(homeView).showSchoolTypeEducation(SCHOOL_TYPE)

    }


    private fun getListOfStudentSuccessfully() {
        val success = argumentCaptor<(List<Student>) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        val listOfStudent = Mockito.mock(listOf<Student>()::class.java)
        whenever(homeDataSource.getListOfStudent(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(listOfStudent)
            mockDisposable
        }
    }

    private fun getListOfStudentUnsuccessfully() {
        val responseThrowable = Mockito.mock(Throwable::class.java)
        whenever(responseThrowable.message).thenReturn(THIS_FAILED)
        val success = argumentCaptor<(List<Student>) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(homeDataSource.getListOfStudent(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(responseThrowable)
            mockDisposable
        }
    }

    companion object {
        const val FAIL = "ALGO SALIO MAL %s"
        const val THIS_FAILED = "FALLA, NO SE OBTUVO LA LISTA"
        const val SCHOOL_NAME = "SchoolName"
        const val SCHOOL_TYPE = "TypeEducation"
    }
}


