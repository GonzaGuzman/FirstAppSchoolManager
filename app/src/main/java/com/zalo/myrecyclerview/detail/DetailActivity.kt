package com.zalo.myrecyclerview.detail


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.databinding.ActivityDetailBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailActivity : GeneralActivity() {


    private lateinit var binding: ActivityDetailBinding
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrieverExtras()
        initComponent()
    }

    private fun retrieverExtras() {
        binding.etId.hint = intent.getIntExtra("itemId", 0).toString()
        binding.etName.hint = intent.getStringExtra("itemNAME").toString()
        binding.etLastName.hint = intent.getStringExtra("itemLAST_NAME").toString()
        binding.etAge.hint = intent.getIntExtra("itemAGE", 0).toString()
        binding.etGender.hint = intent.getStringExtra("itemGENDER").toString()

    }

    private fun initComponent() {
        dataBase.studentDao().getById(intent.getIntExtra("itemId", 0))
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ student = it }, {
                println(it.message)
            })

        binding.btnRemove.setOnClickListener {
            CompositeDisposable()
                .add(
            dataBase.studentDao().delete(student)

              //  .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeAndLogErrors {
                    "Datos Eliminados".showMessagge(this)
                    finish()
                }
)
        }

        binding.btnModify.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            startActivity(intent)
        }
    }
}
/*
fun <T>Single<T>.subscribeAndLogErrors(block: (T) -> Unit): Disposable {
    return this.subscribe(
        { block(it) },
        { println(it.message) }
    )
}
*/
fun String.showMessagge(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}