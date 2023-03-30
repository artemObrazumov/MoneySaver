package com.borsch_team.moneysaver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class SyncronizationViewModel: ViewModel() {
    init {
        if (!App.hasCheckedForPlannedTransactions) {
            App.onTransactionsChecked()
            checkForPlannedTransactions()
        }
    }

    private fun checkForPlannedTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val plannedTransactions = App.api.getPlannedTransactions()
            val currentTime = Calendar.getInstance().timeInMillis
            plannedTransactions.forEach {
                if (it.transaction.date!! < currentTime) {
                    App.api.performPlannedTransaction(it.transaction)
                }
            }
        }
    }
}