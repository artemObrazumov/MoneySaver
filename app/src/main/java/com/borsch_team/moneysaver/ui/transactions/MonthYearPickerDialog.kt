package com.borsch_team.moneysaver.ui.transactions

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.borsch_team.moneysaver.databinding.DialogMonthYearPickerBinding
import java.util.*

class MonthYearPickerDialog(val date: Date = Date()) : DialogFragment() {

    companion object {
        private const val MAX_YEAR = 2099
        private const val MIN_YEAR = 2016
    }

    private lateinit var binding: DialogMonthYearPickerBinding

    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMonthYearPickerBinding.inflate(layoutInflater)

        val cal: Calendar = Calendar.getInstance().apply { time = date }

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
            value = cal.get(Calendar.MONTH)
            displayedValues = arrayOf("Январь","Февраль","Март","Апрель","Май","Июнь","Июль",
                "Август","Сентябрь","Октябрь","Ноябрь","Декабрь")
        }

        binding.pickerYear.run {
            val year = cal.get(Calendar.YEAR)
            minValue = MIN_YEAR
            maxValue = MAX_YEAR
            value = year
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Выберите месяц")
            .setView(binding.root)
            .setPositiveButton("Сохранить") { _, _ -> listener?.onDateSet(null, binding.pickerYear.value, binding.pickerMonth.value, 1) }
            .setNegativeButton("Отмена") { _, _ -> dialog?.cancel() }
            .create()
    }
}