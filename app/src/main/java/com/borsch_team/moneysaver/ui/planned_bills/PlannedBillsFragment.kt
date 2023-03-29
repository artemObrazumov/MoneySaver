package com.borsch_team.moneysaver.ui.planned_bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.databinding.FragmentPlannedBillsBinding
import com.borsch_team.moneysaver.ui.adapter.TransactionPlannedAdapter

class PlannedBillsFragment : Fragment() {

    private lateinit var binding: FragmentPlannedBillsBinding
    private lateinit var viewModel: PlannedBillsViewModel
    private lateinit var adapter: TransactionPlannedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[PlannedBillsViewModel::class.java]
        binding = FragmentPlannedBillsBinding.inflate(inflater, container, false)
        adapter = TransactionPlannedAdapter { item ->

        }
        viewModel.plannedTransactions.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }
        viewModel.getPlannedTransactions()
        return binding.root
    }
}