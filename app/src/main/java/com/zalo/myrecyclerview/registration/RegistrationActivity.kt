package com.zalo.myrecyclerview.registration

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.zalo.myrecyclerview.GeneralActivity
import com.zalo.myrecyclerview.R
import com.zalo.myrecyclerview.databinding.ActivityRegistrationBinding
import com.zalo.myrecyclerview.home.HomeActivity
import com.zalo.myrecyclerview.util.MySharedPreferences
import com.zalo.myrecyclerview.util.showMessage

class RegistrationActivity : GeneralActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponent()
        binding.schoolNameEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun initComponent() {

        if (sharedPreferences.schoolName.isNotEmpty()) {
            binding.schoolNameEditText.isEnabled = false
            binding.primaryCheck.isEnabled = false
            binding.highSchoolCheck.isEnabled = false
            binding.bothOfThemCheck.isEnabled = false
            binding.schoolNameEditText.setText(MySharedPreferences().schoolName)
        }

        binding.btnEnd.setOnClickListener {
            finish()
        }

        binding.btnCloseSession.setOnClickListener {
            closeSession()
        }


        binding.btnContinue.setOnClickListener {
            val nameSchool = binding.schoolNameEditText.text.toString()
            val typeEdu = binding.typeEduOptions.checkedRadioButtonId.toString()
            val intent = Intent(this, HomeActivity::class.java)
            if (nameSchool.isEmpty()) {
                binding.schoolNameEditText.error =
                    getString(R.string.errorMessageEmptyNameSchool)
            } else {
                MySharedPreferences().schoolName = nameSchool
                MySharedPreferences().typeEducation = typeEdu
                startActivity(intent)
                finish()
            }
        }
    }

    private fun closeSession() {
        val positiveButtonClickListener = { _: DialogInterface, _: Int ->
            getString(R.string.yes).showMessage(this)
            binding.primaryCheck.isEnabled = true
            binding.highSchoolCheck.isEnabled = true
            binding.bothOfThemCheck.isEnabled = true
            binding.schoolNameEditText.isEnabled = true
            binding.schoolNameEditText.setText("")
            MySharedPreferences().wipe()
        }
        val negativeButtonClickListener = { dialog: DialogInterface, _: Int ->
            getString(R.string.no).showMessage(this)
            dialog.dismiss()
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.close_session))
        builder.setMessage(getString(R.string.whish_to_continue))
        builder.setPositiveButton(
            getString(R.string.yes),
            DialogInterface.OnClickListener(function = positiveButtonClickListener)
        )
        builder.setNegativeButton(getString(R.string.no), negativeButtonClickListener)
        builder.show()
    }


}