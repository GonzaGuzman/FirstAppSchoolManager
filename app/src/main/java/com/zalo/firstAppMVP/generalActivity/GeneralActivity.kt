package com.zalo.firstAppMVP.generalActivity

import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zalo.firstAppMVP.R
import com.zalo.firstAppMVP.util.sharedPreferences.MySharedPreferences
import com.zalo.firstAppMVP.util.extensions.showMessage

abstract class GeneralActivity : AppCompatActivity() {

    val sharedPreferences = MySharedPreferences()

    // Bloque de codigo para validar la finalizacion de la app al tocar la KEY Back el teclado del movil
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val positiveButtonClickListener = { dialog: DialogInterface, which: Int ->
            getString(R.string.yes).showMessage(this)
            finish()
        }
        val negativeButtonClickListener = { dialog: DialogInterface, which: Int ->
            getString(R.string.no).showMessage(this)
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


     fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}