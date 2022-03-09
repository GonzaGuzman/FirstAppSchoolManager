package com.zalo.myrecyclerview.home

import android.accessibilityservice.AccessibilityGestureEvent
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipboardManager
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.app.NotificationCompat.CATEGORY_MESSAGE
import androidx.core.app.NotificationCompat.getExtras
import androidx.core.view.KeyEventDispatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zalo.myrecyclerview.addStudent.AddStudent
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.AGE
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.GENDER
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.LASTNAME
import com.zalo.myrecyclerview.addStudent.AddStudent.Companion.NAME
import com.zalo.myrecyclerview.databinding.ActivityMainBinding
import com.zalo.myrecyclerview.home.adapter.StudentAdapter
import com.zalo.myrecyclerview.registration.RegistrationActivity.Companion.HIGH_SCHOOL
import com.zalo.myrecyclerview.registration.RegistrationActivity.Companion.NAME_SCHOOL
import com.zalo.myrecyclerview.registration.RegistrationActivity.Companion.PRIMARY

class HomeActivity : AppCompatActivity() {

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

        val schoolName = intent.getStringExtra(NAME_SCHOOL)
        if (intent.getBooleanExtra(PRIMARY, false)) {
            binding.CheckHomePrimary.isChecked = true
        }

        if (intent.getBooleanExtra(HIGH_SCHOOL, false)) {
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
// Bloque de codigo para validar la finalizacion de la app al tocar la KEY Back el teclado del movil

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val positiveButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                "OK", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val negativeButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                "NO", Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        val builder = AlertDialog.Builder(this)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            builder.setTitle("Alerta!!")
            builder.setMessage("DESEA SALIR?")
            builder.setPositiveButton(
                "OK",
                DialogInterface.OnClickListener(function = positiveButtonClickListener)

            )
            builder.setNegativeButton("NO", negativeButtonClickListener)
            builder.show()
        }

        return super.onKeyDown(keyCode, event)
    }

}
