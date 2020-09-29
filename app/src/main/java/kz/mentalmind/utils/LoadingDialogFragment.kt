package kz.mentalmind.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kz.mentalmind.R


class LoadingDialogFragment (context: Context?){

    private val dialog: Dialog
    private val loading: ProgressBar
    private val mainView: View

    init {
        val alertDialog = AlertDialog.Builder(context)
        mainView = LayoutInflater.from(context).inflate(R.layout.fragment_loading_dialog, null)
        loading = mainView.findViewById(R.id.loading)
        dialog = alertDialog.setView(mainView)
            .setCancelable(false)
            .create()
        dialog?.window?.setDimAmount(0.0f)
    }

    fun showLoading() {
        dialog.apply {
            show()
        }
    }

    fun hideLoading() {
        dialog.dismiss()
    }
}