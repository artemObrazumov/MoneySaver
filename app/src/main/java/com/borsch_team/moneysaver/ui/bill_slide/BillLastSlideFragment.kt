package com.borsch_team.moneysaver.ui.bill_slide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.borsch_team.moneysaver.databinding.BillItemBinding
import com.borsch_team.moneysaver.databinding.BillLastItemBinding

class BillLastSlideFragment(
    private val onNewBillClicked: () -> Unit
): Fragment() {
    private lateinit var binding: BillLastItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BillLastItemBinding.inflate(inflater, container, false)
        binding.root.setOnClickListener {
            onNewBillClicked()
        }
        return binding.root
    }
}