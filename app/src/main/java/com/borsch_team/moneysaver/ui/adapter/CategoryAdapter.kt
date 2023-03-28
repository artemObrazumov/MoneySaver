package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.borsch_team.moneysaver.databinding.CategoryItemBinding
import com.borsch_team.moneysaver.databinding.CategoryNewItemBinding

class CategoryAdapter(
    private var categories: List<TransactionCategory>,
    private var onCategoryClicked: (category: TransactionCategory) -> Unit,
    private var onNewCategoryClicked: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CategoryViewHolder(
        private val binding: CategoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            category: TransactionCategory,
            onCategoryClicked: (category: TransactionCategory) -> Unit
        ) {
            binding.icon.setImageDrawable(ContextCompat.getDrawable(binding.root.context,
                Constants.getCategoryImageResource(category.id!!)
            ))
            binding.name.text = category.name
            binding.root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }

    class NewCategoryViewHolder(
        private val binding: CategoryNewItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            onNewCategoryClicked: () -> Unit
        ) {
            binding.root.setOnClickListener {
                onNewCategoryClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> {
                CategoryViewHolder(
                    CategoryItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> {
                NewCategoryViewHolder(
                    CategoryNewItemBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                (holder as CategoryViewHolder).bind(categories[position]) { category ->
                    onCategoryClicked(category)
                }
            }
            1 -> {
                (holder as NewCategoryViewHolder).bind {
                    onNewCategoryClicked()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position < itemCount-1) { 0 }
        else { 1 }

    override fun getItemCount(): Int = categories.size+1

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(categories: List<TransactionCategory>) {
        this.categories = categories
        notifyDataSetChanged()
    }

}