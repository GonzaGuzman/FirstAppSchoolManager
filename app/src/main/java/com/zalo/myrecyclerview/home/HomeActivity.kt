package com.zalo.myrecyclerview.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.databinding.ActivityMainBinding
import com.zalo.myrecyclerview.home.adapter.StudentAdapter
import com.zalo.myrecyclerview.util.MyApplication.Companion.dataBase
import com.zalo.myrecyclerview.util.MySharedPreferences
import com.zalo.myrecyclerview.util.showMessage
import com.zalo.myrecyclerview.util.subscribeAndLogErrors
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeActivity : GeneralActivity() {

    lateinit var studentList: MutableList<Student>
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter
    private var resultLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            getString(R.string.updated_list).showMessage(this)
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

    //hacer una instancia de myShared preferences en el general activity
    //usar also y apply
    private fun retrieveExtras() {

        val schoolName = MySharedPreferences().schoolName
        when (MySharedPreferences().typeEducation) {
            R.id.primaryCheck.toString() -> binding.CheckHomePrimary.isChecked = true
            R.id.highSchoolCheck.toString() -> binding.CheckHomeHighSchool.isChecked = true
            R.id.bothOfThemCheck.toString() -> binding.CheckHomeBothOfThem.isChecked = true
        }
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
    }

    //mejorar
    override fun onResume() {
        super.onResume()
        studentList.clear()
        binding.recycler.layoutManager = LinearLayoutManager(this)

        dataBase.studentDao().getAllStudent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAndLogErrors { students ->
                studentList.addAll(students)
                adapter = StudentAdapter(studentList)
                binding.recycler.adapter = adapter
            }
    }
}
