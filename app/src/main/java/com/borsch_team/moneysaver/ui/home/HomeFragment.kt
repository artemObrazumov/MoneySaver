package com.borsch_team.moneysaver.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import com.borsch_team.moneysaver.databinding.FragmentHomeBinding
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter

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
        adapter = BillsAdapter(requireActivity())
        binding.billsPager.setPageTransformer(MarginPageTransformer(80))
        binding.billsPager.adapter = adapter
        return binding.root
    }
}