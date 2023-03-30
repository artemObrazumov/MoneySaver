package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TransactionAndCategory
import com.borsch_team.moneysaver.databinding.TransactionItemBinding
import kotlin.math.abs

class TransactionTypeAdapter(private val clickedItem: (item: TransactionAndCategory) -> Unit):
    RecyclerView.Adapter<TransactionTypeAdapter.ViewHolder>() {

    private var dataList = emptyList<TransactionAndCategory>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: List<TransactionAndCategory>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: TransactionItemBinding,
        private val clickedItem: (model: TransactionAndCategory) -> Unit
    ):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(moneyTransaction: TransactionAndCategory) {
            binding.tvName.text = moneyTransaction.transaction.name
            binding.tvCategory.text = moneyTransaction.category.name
            if(moneyTransaction.transaction.isExpenses!!){
                binding.tvMoney.text = "- ${abs(moneyTransaction.transaction.money!!)} ₽"
                if (App.preferencesManager.getTheme() == PreferencesManager.THEME_LIGHT) {
                    binding.tvMoney.setTextColor(Color.BLACK)
                } else {
                    binding.tvMoney.setTextColor(Color.WHITE)
                }
            }else{
                binding.tvMoney.text = "+ ${moneyTransaction.transaction.money} ₽"
            }

            binding.imgType.setImageDrawable(
                ContextCompat.getDrawable(binding.root.context,
                Constants.getCategoryImageResource(moneyTransaction.transaction.idCategory!!.toLong())
            ))
            binding.root.setOnClickListener { clickedItem(moneyTransaction) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            TransactionItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), clickedItem
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(item: TransactionAndCategory) {
        val itemId = dataList.indexOf(item)
        this.dataList = dataList.toMutableList().apply {
            removeAt(itemId)
        }.toList()
        notifyDataSetChanged()
    }

}