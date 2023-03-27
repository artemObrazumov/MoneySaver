package com.borsch_team.moneysaver.ui.category_select

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.borsch_team.moneysaver.databinding.FragmentCategorySelectBinding
import com.borsch_team.moneysaver.ui.adapter.CategoryAdapter
import com.borsch_team.moneysaver.ui.category_editor.CategoryEditorActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategorySelectFragment(
    private val onCategorySelected: (category: TransactionCategory) -> Unit
): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCategorySelectBinding
    private lateinit var viewModel: CategorySelectViewModel
    private lateinit var expensesAdapter: CategoryAdapter
    private lateinit var incomesAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategorySelectBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CategorySelectViewModel::class.java]
        viewModel.expenses.observe(viewLifecycleOwner) { categories ->
            expensesAdapter.updateDataset(categories)
        }
        viewModel.incomes.observe(viewLifecycleOwner) { categories ->
            incomesAdapter.updateDataset(categories)
        }
        expensesAdapter = CategoryAdapter(emptyList(), { category ->
            sendCategory(category)
        }) {
            createNewCategory()
        }
        incomesAdapter = CategoryAdapter(emptyList(), { category ->
            sendCategory(category)
        }) {
            createNewCategory()
        }
        binding.expenses.layoutManager = LinearLayoutManager(requireContext())
        binding.incomes.layoutManager = LinearLayoutManager(requireContext())
        binding.expenses.adapter = expensesAdapter
        binding.incomes.adapter = incomesAdapter
        return binding.root
    }

    private fun createNewCategory() {
        startActivity(Intent(requireContext(), CategoryEditorActivity::class.java))
    }

    private fun sendCategory(category: TransactionCategory) {
        onCategorySelected(category)
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCategories()
    }
}