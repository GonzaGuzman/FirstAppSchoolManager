package com.zalo.myrecyclerview

import android.content.DialogInterface
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zalo.myrecyclerview.util.MySharedPreferences
import com.zalo.myrecyclerview.util.showMessage

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
}