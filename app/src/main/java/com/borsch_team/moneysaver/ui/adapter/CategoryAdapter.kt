package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
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
                when (category.id) {
                    1L -> R.drawable.cart_svgrepo_com
                    2L -> R.drawable.fashion_svgrepo_com
                    3L -> R.drawable.flower_svgrepo_com
                    4L -> R.drawable.brush_pencil_svgrepo_com
                    5L -> R.drawable.clipboard_svgrepo_com
                    6L -> R.drawable.cat_icon
                    7L -> R.drawable.car_svgrepo_com
                    8L -> R.drawable.mail_svgrepo_com
                    9L -> R.drawable.pills_pill_svgrepo_com
                    10L -> R.drawable.restaurant_plate_svgrepo_com
                    11L -> R.drawable.arrow_down_svgrepo_com
                    12L -> R.drawable.money_svgrepo_com
                    13L -> R.drawable.zoomout_svgrepo_com
                    14L -> R.drawable.briefcase_svgrepo_com
                    15L -> R.drawable.arrow_down_svgrepo_com
                    16L -> R.drawable.money_svgrepo_com
                    17L -> R.drawable.zoomin_svgrepo_com
                    18L -> R.drawable.booklet_svgrepo_com
                    else -> R.drawable.ic_card
                }
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