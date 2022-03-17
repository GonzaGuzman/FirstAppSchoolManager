package com.zalo.myrecyclerview

import android.content.DialogInterface
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zalo.myrecyclerview.util.MyApplication
import com.zalo.myrecyclerview.util.MySharedPreferences


abstract class GeneralActivity : AppCompatActivity() {


val sharedPreferences= MySharedPreferences()

// Bloque de codigo para validar la finalizacion de la app al tocar la KEY Back el teclado del movil

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val positiveButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                getString(R.string.yes), Toast.LENGTH_LONG
            ).show()
            finish()
        }
        val negativeButtonClickListener = { dialog: DialogInterface, which: Int ->
            Toast.makeText(
                applicationContext,
                getString(R.string.no), Toast.LENGTH_LONG
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
}