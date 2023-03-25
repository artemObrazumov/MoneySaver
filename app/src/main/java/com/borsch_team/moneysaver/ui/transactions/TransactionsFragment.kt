package com.borsch_team.moneysaver.ui.transactions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.FragmentTransactionsBinding
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter
import com.borsch_team.moneysaver.ui.adapter.TransactionPagerAdapter
import com.borsch_team.moneysaver.ui.bill_detail.BillDetailFragment
import com.borsch_team.moneysaver.ui.bill_editor.BillEditorActivity
import com.google.android.material.tabs.TabLayoutMediator

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionsViewModel
    private lateinit var adapter: BillsAdapter
    private lateinit var pagerAdapter: TransactionPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        viewModel.bills.observe(viewLifecycleOwner) { bills ->
            adapter.updateDataset(bills)
        }
        adapter = BillsAdapter(emptyList(), { bill ->
            val fragment = BillDetailFragment(bill) {
                editBill(it)
            }
            fragment.show(childFragmentManager, "bill_info: ${bill.id}")
        }) {
            startActivity(Intent(requireContext(), BillEditorActivity::class.java))
        }
        binding.billsPager.setPageTransformer(MarginPageTransformer(40))
        binding.billsPager.adapter = adapter
        TabLayoutMediator(binding.tabDots, binding.billsPager, true) { _, _ -> }.attach()

        pagerAdapter = TransactionPagerAdapter(requireActivity())
        binding.transactionsPagerAdapter.adapter = pagerAdapter

        initializeTabs()
        viewModel.getBills()


        return binding.root
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
        viewModel.getBills()
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
        val selectedColor = binding.tabExpenses.textColors
        val unselectedColor = binding.tabIncome.textColors

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