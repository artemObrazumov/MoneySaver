package com.borsch_team.moneysaver.ui.transactions

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.databinding.FragmentTransactionsBinding
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter
import com.borsch_team.moneysaver.ui.adapter.TransactionPagerAdapter
import com.borsch_team.moneysaver.ui.bill_detail.BillDetailFragment
import com.borsch_team.moneysaver.ui.bill_editor.BillEditorActivity
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionsViewModel
    private lateinit var adapter: BillsAdapter
    private lateinit var pagerAdapter: TransactionPagerAdapter
    private lateinit var selectedColor: ColorStateList
    private lateinit var unselectedColor: ColorStateList

    private lateinit var selectedBillID: String
    private lateinit var timeRange: TimeRange
    private var childFragmentsInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        binding.billsPager.setPageTransformer(MarginPageTransformer(40))
        adapter = BillsAdapter(emptyList(), { bill ->
            val fragment = BillDetailFragment(bill) {
                editBill(it)
            }
            fragment.show(childFragmentManager, "bill_info: ${bill.id}")
        }, {
            startActivity(Intent(requireContext(), BillEditorActivity::class.java))
        })
        binding.billsPager.adapter = adapter
        binding.billsPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                try {
                    onBillChanged(adapter.getBill(position))
                } catch (_: java.lang.Exception) {}
            }
        })
        TabLayoutMediator(binding.tabDots, binding.billsPager, true) { _, _ -> }.attach()
        findCurrentTimeRange()
        initializePager()
        initializeTabs()
        viewModel.bills.observe(viewLifecycleOwner) { bills ->
            adapter.updateDataset(bills)
            //onBillChanged(bills.first())
        }
        selectedColor = binding.tabExpenses.textColors
        unselectedColor = binding.tabIncome.textColors
        return binding.root
    }

    private fun initializePager() {
        binding.transactionsPagerAdapter.offscreenPageLimit = 2
        pagerAdapter = TransactionPagerAdapter(requireActivity()) {
            childFragmentsInitialized = true
        }
        viewModel.getBills()
        binding.transactionsPagerAdapter.adapter = pagerAdapter
    }

    private fun findCurrentTimeRange(
        calendar: Calendar = Calendar.getInstance()
    ) {
        timeRange = TimeRange(
            calendar.apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis,
            calendar.apply {
                set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE))
                set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND))
                set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND))
            }.timeInMillis
        )
    }

    private fun onBillChanged(bill: Bill) {
        pagerAdapter.updateAdapter(bill.id!!, timeRange)
    }

    private fun editBill(billId: Long) {
        startActivity(
            Intent(
                requireContext(), BillEditorActivity::class.java
            ).apply { putExtra("billId", billId) }
        )
    }

    override fun onResume() {
        super.onResume()
        if (childFragmentsInitialized) {
            viewModel.getBills()
        }
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
}