package com.borsch_team.moneysaver.ui.transactions.transaction_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.databinding.FragmentTransactionTypeBinding
import com.borsch_team.moneysaver.ui.adapter.TransactionTypeAdapter
import java.sql.Time
import com.borsch_team.moneysaver.ui.transaction_detail.TransactionDetailFragment

class TransactionTypeFragment(
    private val isExpenses: Boolean,
    private val onCreated: () -> Unit,
    private val onTransactionRemoved: () -> Unit
): Fragment() {

    private lateinit var binding: FragmentTransactionTypeBinding
    private lateinit var viewModel: TransactionTypeViewModel
    private lateinit var adapter: TransactionTypeAdapter

    private var savedBillId: Long? = null
    private var savedTimeRange: TimeRange? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionTypeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TransactionTypeViewModel::class.java]
        adapter = TransactionTypeAdapter {
            TransactionDetailFragment(it) {
                adapter.removeItem(it)
                onTransactionRemoved()
            }.show(childFragmentManager, "tag")
        }

        if(isExpenses){
            viewModel.arrExpenses.observe(viewLifecycleOwner) {
                val data = it
                adapter.setDataList(data)
            }
        }else{
            viewModel.arrIncome.observe(viewLifecycleOwner){
                adapter.setDataList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        onCreated()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        try {
            updateFragment(savedBillId!!, savedTimeRange!!)
        } catch (_:Exception) {}
    }

    fun updateFragment(billID: Long, timeRange: TimeRange) {
        try {
            savedBillId = billID
            savedTimeRange = timeRange
            if (isExpenses) {
                viewModel.loadSpecifiedExpenses(billID, timeRange)
            } else {
                viewModel.loadSpecifiedIncomes(billID, timeRange)
            }
        } catch (e: Exception) {e.printStackTrace()}
    }
}