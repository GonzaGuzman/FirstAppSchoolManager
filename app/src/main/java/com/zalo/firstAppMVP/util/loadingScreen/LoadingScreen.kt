package com.zalo.firstAppMVP.util.loadingScreen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.zalo.firstAppMVP.R

object LoadingScreen {
    private var dialog: Dialog? = null //obj
    fun displayLoadingWithText(
        context: Context?,
        text: String?,
        cancelable: Boolean,
    ) { // function -- context(parent (reference))
        dialog = context?.let { it -> Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.layout_loding_screen)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(cancelable)
        val textView = dialog?.findViewById<TextView>(R.id.text)
        textView?.text = text
        try {
            dialog?.show()
        } catch (e: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog?.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}