package com.borsch_team.moneysaver.ui.bill_slide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.borsch_team.moneysaver.databinding.BillItemBinding

class BillSlideFragment(

): Fragment() {
    private lateinit var binding: BillItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BillItemBinding.inflate(inflater, container, false)
        return binding.root
    }
}