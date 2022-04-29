package com.zalo.firstAppMVP

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.detail.detailDataSource.DetailDataSource
import com.zalo.firstAppMVP.detail.detailPresenter.DetailActions
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailPresenterTest {
    @Mock
    private lateinit var detailRepository: DetailRepository

    @Mock
    private lateinit var detailView: DetailView

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var detailDataSource: DetailDataSource

    @Mock
    private lateinit var mockDisposable: Disposable

    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        detailPresenter =
            DetailPresenter(detailView, detailDataSource, resources)
    }

    @Test
    fun `getBAyaydiOk`() {
        val student = Mockito.mock(Student::class.java).apply {
            whenever(this.id).thenReturn(1)
            whenever(this.name).thenReturn("Gonza")
        }
        val successCaptor = argumentCaptor<(Student) -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSource.getStudentById(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer{
            successCaptor.firstValue.invoke(student)
            mockDisposable
        }
    }

    @Test
    fun `getBAyaydiFailure`() {
        val student = Mockito.mock(Student::class.java).apply {
            whenever(this.id).thenReturn(0)
            whenever(this.name).thenReturn(null)
        }
        val successCaptor = argumentCaptor<(Student) -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSource.getStudentById(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer{
            errorCaptor.firstValue.invoke(error(any()))
            mockDisposable
        }
    }

    //GIVEN


    /*
    fun getStudentById(id: Int) {
        compositeDisposable.add(
            detailRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _student.value = it
                    view.initView(it)
                }, { error ->
                    view.showErrorSnackBar(String.format(resources.getString(R.string.error_message),
                        error.message))
                }
                )
        )
    }*/
}