package com.borsch_team.moneysaver.ui.planned_bills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.TransactionAndCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlannedBillsViewModel : ViewModel() {
    val plannedTransactions: MutableLiveData<List<TransactionAndCategory>> = MutableLiveData()

    fun getPlannedTransactions() {
        viewModelScope.launch (Dispatchers.IO) {
            plannedTransactions.postValue(App.api.getPlannedTransactions())
        }
    }
}