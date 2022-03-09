package com.zalo.myrecyclerview.addStudent


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.ActivityAddStudentBinding

class AddStudent : AppCompatActivity() {
    companion object {
        const val NAME = "NAME"
        const val LASTNAME = "LASTNAME"
        const val AGE = "AGE"
        const val GENDER = "GENDER"
    }

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
            intent.putExtra(NAME, inputTextName.text.toString())
            intent.putExtra(LASTNAME, inputTextLastName.text.toString())
            intent.putExtra(AGE, inputTextAge.text.toString().toInt())
            intent.putExtra(GENDER, genderDate)
            setResult(RESULT_OK, intent)
            finish()


        }


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val positiveButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                getString(R.string.yes), Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        val negativeButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                getString(R.string.no), Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        val builder = AlertDialog.Builder(this)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            builder.setTitle(getString(R.string.warningText))
            builder.setMessage(getString(R.string.closeApp))
            builder.setPositiveButton(
                getString(R.string.yes),
                DialogInterface.OnClickListener(function = positiveButtonClickListener)

            )
            builder.setNegativeButton(getString(R.string.no), negativeButtonClickListener)
            builder.show()
        }

        return super.onKeyDown(keyCode, event)
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
