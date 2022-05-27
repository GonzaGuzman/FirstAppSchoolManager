package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.detail.detailDataSource.DetailDataSourceImplements
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.disposables.Disposable
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
    private lateinit var detailDataSourceImplements: DetailDataSourceImplements

    @Mock
    private lateinit var mockDisposable: Disposable

    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        detailPresenter =
            DetailPresenter(detailView, detailDataSourceImplements, resources)
    }


    @Test
    fun `getStudentById is success`() {
        //GIVEN
        val id = 2
        val student = Mockito.mock(Student::class.java)
        getStudentByIdUnsuccessfully()
        whenever(resources.getString(R.string.error_message)).thenReturn("error")
        //WHEN
        whenever(detailPresenter.getStudentById(id))

        //THEN
        verify(detailView).showSnackBar(any())

    }


/*
 @Test
    fun ``() {
        //GIVEN

        //WHEN

        //THEN


    }
*/

/*
 @Test
    fun ``() {
        //GIVEN

        //WHEN

        //THEN


    }
*/


    fun getStudentByIdSuccessfully() {
        val student = Mockito.mock(Student::class.java)
        /*.apply {
        whenever(this.id).thenReturn(1)
        whenever(this.name).thenReturn("Gonza")
    }*/
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


    fun getStudentByIdUnsuccessfully() {
        val fail = Mockito.mock(Throwable::class.java)
        val successCaptor = argumentCaptor<(Student) -> Unit>()
        val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

        whenever(
            detailDataSourceImplements.getStudentById(
                any(),
                successCaptor.capture(),
                errorCaptor.capture()
            )
        ).thenAnswer {
            errorCaptor.firstValue.invoke(fail)
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