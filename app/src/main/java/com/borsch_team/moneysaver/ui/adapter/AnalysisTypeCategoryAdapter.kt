package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.borsch_team.moneysaver.data.models.AnalysisTransaction
import com.borsch_team.moneysaver.databinding.AnalysisTransactionItemBinding

class AnalysisTypeCategoryAdapter(): RecyclerView.Adapter<AnalysisTypeCategoryAdapter.ViewHolder>(){

    private var dataList = emptyList<AnalysisTransaction>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setDataList(dataList: List<AnalysisTransaction>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: AnalysisTransactionItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(analysisTransaction: AnalysisTransaction){
            binding.tvAnalysisCategory.text = analysisTransaction.name
            binding.tvAnalysisMoney.text = analysisTransaction.money.toString()
            binding.imgAnalysisCategory.setImageResource(analysisTransaction.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AnalysisTransactionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}