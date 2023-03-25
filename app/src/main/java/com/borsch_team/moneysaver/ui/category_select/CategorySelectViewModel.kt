package com.borsch_team.moneysaver.ui.category_select

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.TransactionCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategorySelectViewModel: ViewModel() {
    val expenses: MutableLiveData<List<TransactionCategory>> = MutableLiveData()
    val incomes: MutableLiveData<List<TransactionCategory>> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            expenses.postValue(App.api.getExpensesCategory())
            incomes.postValue(App.api.getIncomesCategory())
        }
    }
}