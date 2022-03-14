package com.zalo.myrecyclerview.home


import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.AGE
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.GENDER
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.LASTNAME
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.NAME
import com.zalo.myrecyclerview.databinding.ActivityMainBinding
import com.zalo.myrecyclerview.home.adapter.StudentAdapter
import com.zalo.myrecyclerview.util.MySharedPreferences

class HomeActivity : GeneralActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<Student>
    private lateinit var adapter: StudentAdapter
    private var resultLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val nameStudent = it.data?.getStringExtra(NAME).orEmpty()
            val lastNameStudent = it.data?.getStringExtra(LASTNAME).orEmpty()
            val ageStudent = it.data?.getIntExtra(AGE, 0) ?: 0
            val genderStudent = it.data?.getStringExtra(GENDER).orEmpty()
            list.add(Student(nameStudent, lastNameStudent, ageStudent, genderStudent))
            adapter.notifyDataSetChanged()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        list = arrayListOf()
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            resultLauncher.launch(intent)
        }
        adapter = StudentAdapter(list)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

    }


}
