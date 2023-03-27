package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.databinding.TransactionItemBinding

class TransactionTypeAdapter(private val clickedItem: (item: MoneyTransaction) -> Unit):
    RecyclerView.Adapter<TransactionTypeAdapter.ViewHolder>() {

    private var dataList = emptyList<MoneyTransaction>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: List<MoneyTransaction>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TransactionItemBinding, private val clickedItem: (model: MoneyTransaction) -> Unit):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moneyTransaction: MoneyTransaction) {
            binding.tvName.text = moneyTransaction.name
            binding.tvCategory.text = moneyTransaction.idCategory.toString()
            binding.imgType.setImageResource(if(moneyTransaction.isExpenses!!) R.drawable.baseline_add_circle_outline_24 else R.drawable.baseline_analytics_24)
            if(moneyTransaction.isExpenses!!){
                binding.tvMoney.text = "- ${moneyTransaction.money} ₽"
                binding.tvMoney.setTextColor(Color.BLACK)
            }else{
                binding.tvMoney.text = "+ ${moneyTransaction.money} ₽"
                binding.tvMoney.setTextColor(Color.GREEN)
            }
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

}