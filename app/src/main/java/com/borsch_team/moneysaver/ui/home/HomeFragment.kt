package com.borsch_team.moneysaver.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.FragmentHomeBinding
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter
import com.borsch_team.moneysaver.ui.bill_editor.BillEditorActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: BillsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = BillsAdapter(requireActivity()) {
            startActivity(Intent(requireContext(), BillEditorActivity::class.java))
        }
        binding.billsPager.setPageTransformer(MarginPageTransformer(40))
        binding.billsPager.adapter = adapter
        TabLayoutMediator(binding.tabDots, binding.billsPager, true) { _, _ -> }.attach()
        initializeTabs()
        return binding.root
    }

    private fun initializeTabs() {
        val selectedColor = binding.tabExpenses.textColors
        val unselectedColor = binding.tabIncome.textColors
        val tabListener = OnClickListener {
            when (it.id) {
                R.id.tab_expenses -> {
                    binding.tabSelector.animate().x(0f).duration = 100L
                    binding.tabExpenses.setTextColor(selectedColor)
                    binding.tabIncome.setTextColor(unselectedColor)
                }
                R.id.tab_income -> {
                    val size = binding.tabIncome.width.toFloat()
                    binding.tabSelector.animate().x(size).duration = 100L
                    binding.tabExpenses.setTextColor(unselectedColor)
                    binding.tabIncome.setTextColor(selectedColor)
                }
            }
        }
        binding.tabExpenses.setOnClickListener(tabListener)
        binding.tabIncome.setOnClickListener(tabListener)
    }
}