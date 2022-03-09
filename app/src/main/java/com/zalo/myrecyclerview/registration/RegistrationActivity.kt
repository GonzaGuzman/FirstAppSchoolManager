package com.zalo.myrecyclerview.registration

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.ActivityRegistrationBinding
import com.zalo.myrecyclerview.home.HomeActivity

class RegistrationActivity : AppCompatActivity() {

    companion object {
        const val PRIMARY = "PRIMARY"
        const val HIGH_SCHOOL = "HIGH_SCHOOL"
        const val NAME_SCHOOL = "NAME_SCHOOL"
    }

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponent()
    }

    fun initComponent() {
        binding.endButton.setOnClickListener {
            finish()
        }

        binding.continueButton.setOnClickListener {
            val nameSchool = binding.schoolNameEditText.text.toString()
            val primary = binding.primaryCheck.isChecked
            val highSchool = binding.highSchoolCheck.isChecked
            val intent = Intent(this, HomeActivity::class.java)
            if (nameSchool.isEmpty()) {
                binding.schoolNameEditText.error = getString(R.string.errorMessageEmptyNameSchool)
            } else {
                intent.putExtra(PRIMARY, primary)
                intent.putExtra(HIGH_SCHOOL, highSchool)
                intent.putExtra(NAME_SCHOOL, nameSchool)
                startActivity(intent)
                finish()
            }
        }
    }


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