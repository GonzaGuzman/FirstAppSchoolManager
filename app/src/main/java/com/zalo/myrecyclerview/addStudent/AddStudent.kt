package com.zalo.myrecyclerview.addStudent


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.ActivityAddStudentBinding
import com.zalo.myrecyclerview.home.Student
import com.zalo.myrecyclerview.util.MyApplication

class AddStudent : GeneralActivity() {


    private lateinit var inputTextName: EditText
    private lateinit var inputTextLastName: EditText
    private lateinit var inputTextAge: EditText

    private lateinit var action_button: Button

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponent()

    }

    private fun initComponent() {
        val intent = Intent()

        inputTextName = binding.studentName
        inputTextLastName = binding.studentLastName
        inputTextAge = binding.studentAge
        action_button = binding.aggregateButton
        inputTextName.addTextChangedListener(textWatcher)
        inputTextLastName.addTextChangedListener(textWatcher)
        inputTextAge.addTextChangedListener(textWatcher)

        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        action_button.setOnClickListener {

            val genderDate = when (binding.radioGroupGender.checkedRadioButtonId) {
                binding.radioButtonFemale.id -> getString(R.string.femaleText)
                binding.radioButtonMale.id -> getString(R.string.maleText)
                else -> getString(R.string.noGenederText)
            }

            val student = Student(
                0,
                inputTextName.text.toString(),
                inputTextLastName.text.toString(),
                inputTextAge.text.toString().toInt(),
                genderDate
            )
            MyApplication.dataBase.studentDao().insert(student)
            setResult(RESULT_OK, intent)
            finish()


        }


    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            action_button.isEnabled =
                inputTextName.text.isNotEmpty() && inputTextLastName.text.isNotEmpty() && inputTextAge.text.isNotEmpty()
        }
    }


}
