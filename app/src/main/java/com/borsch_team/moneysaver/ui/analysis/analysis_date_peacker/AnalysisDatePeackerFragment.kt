package com.borsch_team.moneysaver.ui.analysis.analysis_date_peacker

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.databinding.FragmentAnalysisDatePeackerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AnalysisDatePeackerFragment(
    private val startTime: Long,
    private val endTime: Long,
    private val time: (timeRange: TimeRange) -> Unit): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAnalysisDatePeackerBinding
    private var timeRange = TimeRange(startTime, endTime)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisDatePeackerBinding.inflate(layoutInflater)
        updateUI(timeRange)

        binding.btnEditOt.setOnClickListener {
            showDatePeacker(true)
        }

        binding.btnEditDo.setOnClickListener {
            showDatePeacker(false)
        }

        binding.btnSave.setOnClickListener {
            if (timeRange.startTimestamp > timeRange.endTimestamp){
                Toast.makeText(context, "Выбран неккоректный диапазон!", Toast.LENGTH_SHORT).show()
            }else{
                time(timeRange)
                dismiss()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUI(timeRange: TimeRange){
        binding.tvDateOt.text = SimpleDateFormat("dd/M/yyyy").format(timeRange.startTimestamp)
        binding.tvDateDo.text = SimpleDateFormat("dd/M/yyyy").format(timeRange.endTimestamp)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePeacker(isOt: Boolean){
        var newDate: Long = 0
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            newDate = calendar.time.time
            if(isOt){
                timeRange.startTimestamp = newDate
                updateUI(timeRange)
            }else{
                timeRange.endTimestamp = newDate
                updateUI(timeRange)
            }
        }

        DatePickerDialog(requireContext(), dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}