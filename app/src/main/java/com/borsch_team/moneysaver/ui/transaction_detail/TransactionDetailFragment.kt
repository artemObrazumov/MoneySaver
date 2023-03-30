package com.borsch_team.moneysaver.ui.transaction_detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.FieldClassification.Match
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.data.models.TransactionAndCategory
import com.borsch_team.moneysaver.databinding.FragmentTransactionDetailBinding
import com.borsch_team.moneysaver.ui.dialog.CompletedDialog
import com.borsch_team.moneysaver.ui.dialog.WarningDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.abs

class TransactionDetailFragment(
    private val data: TransactionAndCategory,
    private val onTransactionDeleted: () -> Unit
    ) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransactionDetailBinding
    private lateinit var viewModel: TransactionDetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this) [TransactionDetailViewModel::class.java]
        binding = FragmentTransactionDetailBinding.inflate(layoutInflater)
        binding.tvTransName.text = data.transaction.name
        binding.avatar.setImageDrawable(
            ContextCompat.getDrawable(binding.root.context,
            Constants.getCategoryImageResource(data.category.id!!)
        ))
        binding.tvTransCategory.text = data.category.name
        if(data.transaction.isExpenses!!){
            binding.tvTransMoney.text = "- ${abs(data.transaction.money!!)} ₽"
            if (PreferencesManager.getTheme(binding.root.context) == PreferencesManager.THEME_LIGHT) {
                binding.tvTransMoney.setTextColor(Color.BLACK)
            } else {
                binding.tvTransMoney.setTextColor(Color.WHITE)
            }
        }else{
            binding.tvTransMoney.text = "+ ${data.transaction.money} ₽"
        }
        binding.deleteButton.setOnClickListener {
            WarningDialog("Вы уверены, что хотите удалить эту операцию?") {
                viewModel.deleteTransaction(data.transaction)
                CompletedDialog("Операция удалена") {
                    dismiss()
                }.show(childFragmentManager, "completed")
                onTransactionDeleted()
            }.show(childFragmentManager, "delete_transaction: ${data.transaction.id}")
        }
        customizeDialogState()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    private fun customizeDialogState() {
        dialog?.setOnShowListener {
            binding.root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        }
    }
}