package com.borsch_team.moneysaver.ui.bill_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.databinding.FragmentBillDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BillDetailFragment(
    private val bill: Bill,
    private val onEditClicked: (id: Long) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBillDetailBinding
    private lateinit var viewModel: BillDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[BillDetailViewModel::class.java]
        viewModel.bill.observe(viewLifecycleOwner) { updatedBill ->
            if (updatedBill != null) {
                binding.name.text = updatedBill.name
                binding.balance.text = updatedBill.balance.toString()
                when (updatedBill.idType) {
                    Constants.BILL_TYPE_CARD -> {
                        binding.cardBackground.background = ContextCompat
                            .getDrawable(binding.root.context, R.drawable.bill_background_card_flat)
                    }
                    Constants.BILL_TYPE_BANKNOTES -> {
                        binding.cardBackground.background = ContextCompat
                            .getDrawable(binding.root.context, R.drawable.bill_background_banknotes_flat)
                    }
                }
            } else {
                dismiss()
            }
        }
        binding.edit.setOnClickListener {
            onEditClicked(bill.id!!)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadBill(bill.id!!)
    }

}