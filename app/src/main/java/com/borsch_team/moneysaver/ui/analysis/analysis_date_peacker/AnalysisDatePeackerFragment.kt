package com.borsch_team.moneysaver.ui.analysis.analysis_date_peacker

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.databinding.FragmentAnalysisDatePeackerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AnalysisDatePeackerFragment(private val time: (timeRange: TimeRange) -> Unit): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAnalysisDatePeackerBinding

    private var dateOt: Long = 0
    private var dateDo: Long = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisDatePeackerBinding.inflate(layoutInflater)

        binding.btnEditOt.setOnClickListener {
            binding.tvDateOt.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

            val cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                cal.set(java.util.Calendar.HOUR_OF_DAY, cal.getActualMaximum(java.util.Calendar.HOUR_OF_DAY))
                cal.set(java.util.Calendar.MINUTE, cal.getActualMaximum(java.util.Calendar.MINUTE))
                cal.set(java.util.Calendar.SECOND, cal.getActualMaximum(java.util.Calendar.SECOND))
                cal.set(java.util.Calendar.MILLISECOND, cal.getActualMaximum(java.util.Calendar.MILLISECOND))

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.tvDateOt.text = sdf.format(cal.time)
                dateOt = cal.time.time
            }

            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnEditDo.setOnClickListener {
            binding.tvDateDo.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

            val cal = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.tvDateDo.text = sdf.format(cal.time)
                dateDo = cal.time.time
            }

            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnSave.setOnClickListener {
            time(TimeRange(dateOt, dateDo))
            dismiss()
        }

        return binding.root
    }
}