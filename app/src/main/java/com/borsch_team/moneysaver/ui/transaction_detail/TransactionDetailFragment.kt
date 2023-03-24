package com.borsch_team.moneysaver.ui.transaction_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.borsch_team.moneysaver.databinding.FragmentTransactionDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TransactionDetailFragment(private val data: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransactionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionDetailBinding.inflate(inflater)

        binding.tvData.text = data

        return binding.root
    }
}