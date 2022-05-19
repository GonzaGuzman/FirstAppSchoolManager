package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.home.homeDataSource.HomeDataSource
import com.zalo.firstAppMVP.home.homePresenter.HomePresenter
import com.zalo.firstAppMVP.home.homePresenter.HomeView
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
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

    @InjectMocks
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

        //WHEN
        homePresenter.initComponent()

        //THEN
        verify(homeView).loadRecycler(any())
    }


    @Test
    fun `when initComponent is unsuccessfully`() {
        //GIVEN
        getListOfStudentUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn("This failed")

        //WHEN
        homePresenter.initComponent()

        //THEN
        verify(homeView).showSnackBar(anyString())
    }

    @Test
    fun `when initSchoolDate is call`() {
        //GIVEN
        val name = "SchoolName"
        val type = "TypeEducation"
        whenever(homeDataSource.getSchoolName()).thenReturn(name)
        whenever(homeDataSource.getSchoolTypeEducation()).thenReturn(type)

        //WHEN
        homePresenter.initSchoolDate()

        //THEN
        verify(homeView).showSchoolName(name)
        verify(homeView).showSchoolTypeEducation(type)

    }


    private fun getListOfStudentSuccessfully() {
        val success = argumentCaptor<(List<Student>) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(homeDataSource.getListOfStudent(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            success.firstValue.invoke(listOf())
            mockDisposable
        }
    }

    private fun getListOfStudentUnsuccessfully() {
        val fail = Mockito.mock(Throwable::class.java)
        val success = argumentCaptor<(List<Student>) -> Unit>()
        val error = argumentCaptor<(Throwable) -> Unit>()
        whenever(homeDataSource.getListOfStudent(
            success.capture(),
            error.capture(),
        )).thenAnswer {
            error.firstValue.invoke(fail)
            mockDisposable
        }
    }


}


