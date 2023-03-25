package com.borsch_team.moneysaver.ui.bill_detail

import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.databinding.FragmentBillDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BillDetailFragment(
    private val bill: Bill
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBillDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillDetailBinding.inflate(inflater, container, false)
        binding.name.text = bill.name
        binding.balance.text = bill.balance.toString()
        when (bill.idType) {
            Constants.BILL_TYPE_CARD -> {
                binding.cardBackground.background = ContextCompat
                    .getDrawable(binding.root.context, R.drawable.bill_background_card_flat)
            }
            Constants.BILL_TYPE_BANKNOTES -> {
                binding.cardBackground.background = ContextCompat
                    .getDrawable(binding.root.context, R.drawable.bill_background_banknotes_flat)
            }
        }
        return binding.root
    }

}