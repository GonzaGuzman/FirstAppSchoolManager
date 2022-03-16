package com.zalo.myrecyclerview.home


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.databinding.ActivityMainBinding
import com.zalo.myrecyclerview.home.adapter.StudentAdapter
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase
import com.zalo.myrecyclerview.util.MySharedPreferences

class HomeActivity : GeneralActivity() {

    lateinit var studentList: MutableList<Student>
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter
    private var resultLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            Toast.makeText(this,"Lista Actualizada", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        studentList = ArrayList()
        retrieveExtras()
        initComponents()

    }

    private fun retrieveExtras() {

        val schoolName = MySharedPreferences().schoolName
        if (MySharedPreferences().isPrimary) {
            binding.CheckHomePrimary.isChecked = true
        }

        if (MySharedPreferences().isHighSchool) {
            binding.CheckHomeHighSchool.isChecked = true
        }
        binding.CheckHomePrimary.isEnabled = false
        binding.CheckHomeHighSchool.isEnabled = false
        binding.textViewHome.text = schoolName
    }


    private fun initComponents() {
        binding.endButtonHome.setOnClickListener {
            finish()
        }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            resultLauncher.launch(intent)
        }

        adapter = StudentAdapter(dataBase.studentDao().getAllStudent())
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        adapter = StudentAdapter(dataBase.studentDao().getAllStudent())
        binding.recycler.adapter = adapter

    }

}
