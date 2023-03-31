package com.borsch_team.moneysaver.ui.analysis

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.Utils
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.databinding.FragmentAnalysisBinding
import com.borsch_team.moneysaver.ui.adapter.AnalysisPagerAdapter
import com.borsch_team.moneysaver.ui.adapter.TransactionPagerAdapter
import com.borsch_team.moneysaver.ui.analysis.analysis_date_peacker.AnalysisDatePeackerFragment
import com.bumptech.glide.util.Util
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AnalysisFragment : Fragment() {

    private lateinit var binding: FragmentAnalysisBinding
    private lateinit var viewModel: AnalysisViewModel
    private lateinit var pagerAdapter: AnalysisPagerAdapter
    private lateinit var selectedColor: ColorStateList
    private lateinit var unselectedColor: ColorStateList

    private var startTimestamp: Long = 0
    private var endTimestamp: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AnalysisViewModel::class.java]

        selectedColor = binding.tabExpenses.textColors
        unselectedColor = binding.tabIncome.textColors

        viewModel.mutableTimeRange.observe(requireActivity()){timeRange ->
            startTimestamp = timeRange.startTimestamp
            endTimestamp = timeRange.endTimestamp
        }

        binding.pickDate.setOnClickListener {
            AnalysisDatePeackerFragment(startTimestamp, endTimestamp) {timeRange ->
                startTimestamp = timeRange.startTimestamp
                endTimestamp = timeRange.endTimestamp

                initializePager(startTimestamp, endTimestamp)
            }.show(childFragmentManager, "tag")
        }
        initDate()
        initializeTabs()
        initializePager(startTimestamp, endTimestamp)

        return binding.root
    }

    private fun initDate(){
        binding.tvDate.text = Utils.getActualStringMonthAndYear()
    }

    private fun initializeTabs() {
        val tabListener = View.OnClickListener {
            when (it.id) {
                R.id.tab_expenses -> {
                    tabSelected(0)
                    binding.transactionsPagerAdapter.currentItem = 0
                }
                R.id.tab_income -> {
                    tabSelected(1)
                    binding.transactionsPagerAdapter.currentItem = 1
                }
            }
        }
        binding.tabExpenses.setOnClickListener(tabListener)
        binding.tabIncome.setOnClickListener(tabListener)
        binding.transactionsPagerAdapter.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabSelected(position)
            }
        })
    }
    private fun tabSelected(position: Int){
        if(position == 0){
            binding.tabSelector.animate().x(0f).duration = 100L
            binding.tabExpenses.setTextColor(selectedColor)
            binding.tabIncome.setTextColor(unselectedColor)
        }else{
            val size = binding.tabIncome.width.toFloat()
            binding.tabSelector.animate().x(size).duration = 100L
            binding.tabExpenses.setTextColor(unselectedColor)
            binding.tabIncome.setTextColor(selectedColor)
        }
    }

    private fun initializePager(startTimestamp: Long, endTimestamp: Long) {
        pagerAdapter = AnalysisPagerAdapter(requireActivity(), startTimestamp, endTimestamp)
        binding.transactionsPagerAdapter.adapter = pagerAdapter
        binding.transactionsPagerAdapter.isSaveFromParentEnabled = false
    }
}