package com.borsch_team.moneysaver.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.WarningDialogBinding


class ThemeSelectDialog() : AppCompatDialogFragment() {
    private lateinit var binding: WarningDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = WarningDialogBinding.inflate(layoutInflater)

        return AlertDialog.Builder(this.context)
            .setView(binding.root)
            .create()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}