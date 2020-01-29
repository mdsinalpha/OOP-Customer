package oop.customer.api.progressdialog

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import oop.customer.R
import kotlinx.android.synthetic.main.dialog_horizontal_progress_bar.*

class HorizontalProgressBarDialog(ctx: Context?, private val message: String?): ProgressDialog(ctx, R.style.enthusi4stic_horizontal_progress_bar_dialog){

    companion object{
        const val MAX_PROGRESS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(true)
        setContentView(R.layout.dialog_horizontal_progress_bar)
        message?.let {
            dialog_horizontal_progress_bar_progress_message.text = it
        }
        dialog_horizontal_progress_bar_progress_bar.max = MAX_PROGRESS
    }

    fun animateProgress(newProgress: Int){
        dialog_horizontal_progress_bar_progress_bar.animateProgress(50, newProgress)
    }
}