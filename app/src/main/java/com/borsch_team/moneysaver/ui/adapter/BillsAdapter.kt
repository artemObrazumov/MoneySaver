package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.databinding.BillItemBinding
import com.borsch_team.moneysaver.databinding.BillLastItemBinding

class BillsAdapter(
    private var bills: List<Bill>,
    private val onBillClicked: (bill: Bill) -> Unit,
    private val onNewBillClicked: () -> Unit,
    private var hasEmptyBill: Boolean = true
): RecyclerView.Adapter<ViewHolder>() {

    class BillViewHolder (
        private val binding: BillItemBinding
    ): ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            bill: Bill,
            onBillClciked: (bill: Bill) -> Unit
        ) {
            binding.balance.text = "${bill.balance.toString()} ₽"
            binding.reserved.text = "Резерв: ${bill.reservedMoney.toString()} ₽"
            binding.title.text = bill.name
            when (bill.idType) {
                Constants.BILL_TYPE_CARD -> {
                    binding.root.background = ContextCompat
                        .getDrawable(binding.root.context, R.drawable.bill_background_card)
                    binding.icon.setImageDrawable(ContextCompat
                        .getDrawable(binding.root.context, R.drawable.ic_card))
                }
                Constants.BILL_TYPE_BANKNOTES -> {
                    binding.root.background = ContextCompat
                        .getDrawable(binding.root.context, R.drawable.bill_background_banknote)
                    binding.icon.setImageDrawable(ContextCompat
                        .getDrawable(binding.root.context, R.drawable.ic_banknotes))
                }
            }
            binding.root.setOnClickListener {
                onBillClciked(bill)
            }
        }
    }

    class NewBillViewHolder (
        private val binding: BillLastItemBinding
    ): ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            onNewBillClciked: () -> Unit
        ) {
            binding.root.setOnClickListener {
                onNewBillClciked()
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position < itemCount-1) { 0 }
        else if (!hasEmptyBill) { 0 }
        else { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            0 -> {
                BillViewHolder(
                    BillItemBinding.inflate(LayoutInflater.from(parent.context),
                    parent, false
                ))
            }
            else -> {
                NewBillViewHolder(
                    BillLastItemBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false
                    ))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                (holder as BillViewHolder).bind(bills[position]) { bill ->
                    onBillClicked(bill)
                }
            }
            1 -> {
                (holder as NewBillViewHolder).bind {
                    onNewBillClicked()
                }
            }
        }
    }

    override fun getItemCount(): Int =
        if (hasEmptyBill) {bills.size+1}
        else {bills.size}

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(bills: List<Bill>) {
        this.bills = bills
        notifyDataSetChanged()
    }

    fun getBill(position: Int): Bill = bills[position]
}