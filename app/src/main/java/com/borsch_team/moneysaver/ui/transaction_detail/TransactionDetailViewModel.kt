package com.borsch_team.moneysaver.ui.transaction_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionDetailViewModel: ViewModel() {
    fun deleteTransaction(transaction: MoneyTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            App.api.removeTransaction(transaction)
        }
    }
}