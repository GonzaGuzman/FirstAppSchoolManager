package com.zalo.myrecyclerview.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.set
import com.zalo.myrecyclerview.databinding.ActivityDetailBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
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
        val student = dataBase.studentDao().getById(intent.getIntExtra("itemId", 0))
        binding.btnRemove.setOnClickListener {
            dataBase.studentDao().delete(student)
            Toast.makeText(this, "DATOS ELIMINADOS", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        binding.btnModify.setOnClickListener{

        }

    }
}