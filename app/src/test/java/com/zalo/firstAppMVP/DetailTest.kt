package com.zalo.firstAppMVP

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zalo.firstAppMVP.detail.detailPresenter.DetailActions
import com.zalo.firstAppMVP.detail.detailPresenter.DetailPresenter
import com.zalo.firstAppMVP.detail.detailPresenter.DetailView
import com.zalo.firstAppMVP.detail.detailRepository.DetailRepository
import com.zalo.firstAppMVP.util.dataClassStudent.Student
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailTest {
    @Mock
    private lateinit var detailRepository: DetailRepository

    @Mock
    private lateinit var detailView: DetailView

    @Mock
    private lateinit var resources: Resources

    private lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        detailPresenter =
            DetailPresenter(detailView, detailRepository, resources)
    }

    @Test
    fun `hola`() {
        //GIVEN

        whenever(detailRepository.getById(1)).thenReturn(Single <Student>(0,"","",0,""))

        //WHEN
        detailPresenter.getStudentById(1)

        //THEM
        verify(detailView).initView(Student(0,"","",0,""))


    }

    /*fun getStudentById(id: Int) {
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