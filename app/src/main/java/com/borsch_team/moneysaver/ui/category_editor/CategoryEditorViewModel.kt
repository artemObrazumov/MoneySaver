package com.borsch_team.moneysaver.ui.category_editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryEditorViewModel: ViewModel() {
    fun upsertCategory(category: TransactionCategory) {
        viewModelScope.launch (Dispatchers.IO) {
            App.api.upsertCategory(category)
        }
    }
}