package com.borsch_team.moneysaver.ui.analysis.analysis_type

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.FragmentAnalysisTypeBinding
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import com.faskn.lib.buildChart
import kotlin.random.Random


class AnalysisTypeFragment(private val isExpenses: Boolean,
                           private val startTimestamp: Long,
                           private val endTimestamp: Long): Fragment() {

    private lateinit var binding: FragmentAnalysisTypeBinding
    private lateinit var viewModel: AnalysisTypeViewModel
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisTypeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AnalysisTypeViewModel::class.java]


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData(){
        if(isExpenses){
            viewModel.arrExpenses.observe(requireActivity()){
                if (it.size != 0){
                    initPie(it)
                }else{
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.clickablePieChart.visibility = View.INVISIBLE
                }
            }
            viewModel.getExpenses(startTimestamp, endTimestamp)
        }else{
            viewModel.arrIncomes.observe(requireActivity()){
                if (it.size != 0){
                    initPie(it)
                }else{
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.clickablePieChart.visibility = View.INVISIBLE
                }
            }
            viewModel.getIncomes(startTimestamp, endTimestamp)
        }
    }

    private fun initPie(arrData: ArrayList<Slice>){
        pieChart = buildChart {
            slices {arrData}
            sliceWidth { 80f }
            sliceStartPoint { 0f }
            clickListener { angle, index ->
                //Toast.makeText(requireContext(), index.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.clickablePieChart.setPieChart(pieChart)
    }

    private fun initDataForPie(): ArrayList<Slice>{
        return arrayListOf(
            Slice(
                Random.nextInt(1000, 3000).toFloat(),
                R.color.black,
                "Google"
            ),
            Slice(
                Random.nextInt(1000, 2000).toFloat(),
                R.color.cardBlueDark,
                "Facebook"
            ),
            Slice(
                Random.nextInt(1000, 5000).toFloat(),
                R.color.cardBlueLight,
                "Twitter"
            ),
            Slice(
                Random.nextInt(1000, 10000).toFloat(),
                R.color.cardGreenLight,
                "Other"
            )
        )
    }
}