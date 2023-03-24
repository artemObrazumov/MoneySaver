package com.borsch_team.moneysaver.ui.planned_bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.databinding.FragmentPlannedBillsBinding

class PlannedBillsFragment : Fragment() {

    private lateinit var binding: FragmentPlannedBillsBinding
    private lateinit var viewModel: PlannedBillsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[PlannedBillsViewModel::class.java]
        binding = FragmentPlannedBillsBinding.inflate(inflater, container, false)
        return binding.root
    }
}