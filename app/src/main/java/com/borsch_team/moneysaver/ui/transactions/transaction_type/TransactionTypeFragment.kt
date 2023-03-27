package com.borsch_team.moneysaver.ui.transactions.transaction_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.borsch_team.moneysaver.databinding.FragmentTransactionTypeBinding
import com.borsch_team.moneysaver.ui.adapter.TransactionTypeAdapter
import com.borsch_team.moneysaver.ui.transaction_detail.TransactionDetailFragment

class TransactionTypeFragment(private val isExpenses: Boolean): Fragment() {

    private lateinit var binding: FragmentTransactionTypeBinding
    private lateinit var viewModel: TransactionTypeViewModel
    private lateinit var adapter: TransactionTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionTypeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TransactionTypeViewModel::class.java]
        adapter = TransactionTypeAdapter {
            Toast.makeText(context, "${it.isExpenses.toString()}: ${it.name}", Toast.LENGTH_SHORT).show()
            TransactionDetailFragment(it.name!!).show(childFragmentManager, "tag")
        }

        if(isExpenses){
            viewModel.arrExpenses.observe(viewLifecycleOwner) {
                val data = it
                adapter.setDataList(data)
            }
            viewModel.loadExpenses()
        }else{
            viewModel.arrIncome.observe(viewLifecycleOwner){
                adapter.setDataList(it)
            }
            viewModel.loadIncome()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter


        return binding.root
    }
}