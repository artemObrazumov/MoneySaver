package com.borsch_team.moneysaver.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import com.borsch_team.moneysaver.databinding.FragmentTransactionsBinding
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var viewModel: TransactionsViewModel
    private lateinit var adapter: BillsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        adapter = BillsAdapter(requireActivity())
        binding.billsPager.setPageTransformer(MarginPageTransformer(80))
        binding.billsPager.adapter = adapter
        return binding.root
    }
}