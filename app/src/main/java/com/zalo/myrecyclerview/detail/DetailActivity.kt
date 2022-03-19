package com.zalo.myrecyclerview.detail


import android.content.Intent
import android.os.Bundle
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.databinding.ActivityDetailBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase
import com.zalo.myrecyclerview.util.showMessage
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
        binding.tvIdVisible.text = intent.getIntExtra("itemId", 0).toString()
        binding.tvNameVisible.text = intent.getStringExtra("itemNAME").toString()
        binding.tvLastNameVisible.text = intent.getStringExtra("itemLAST_NAME").toString()
        binding.tvAgeVisible.text = intent.getIntExtra("itemAGE", 0).toString()
        binding.tvGenderVisible.text = intent.getStringExtra("itemGENDER").toString()

    }

    private fun initComponent() {
        dataBase.studentDao().getById(intent.getIntExtra("itemId", 0))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ student = it }, {
                println(it.message)
            })

        binding.btnRemove.setOnClickListener {
            CompositeDisposable()
                .add(
                    dataBase.studentDao().delete(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeAndLogErrors {
                            "Datos Eliminados".showMessage(this)
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
