package com.borsch_team.moneysaver.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.borsch_team.moneysaver.R


class CompletedDialog(private val Text:String, private val clickedExit: () -> Unit) : AppCompatDialogFragment() {
    private lateinit var customView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //set custom xml
        customView = this.layoutInflater.inflate(R.layout.completed_dialog, null)
        //init btns
        val text:TextView=customView.findViewById(R.id.texttttt)
        text.text=Text
        val ok:TextView=customView.findViewById(R.id.ok)
        ok.setOnClickListener{
            clickedExit()
            dismiss()
        }
        return AlertDialog.Builder(this.context)
            .setView(customView)
            .create()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}