package com.borsch_team.moneysaver.ui.bill_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillDetailViewModel: ViewModel() {
    val monthStats: MutableLiveData<BillMonthStats> = MutableLiveData()

    fun getBillMonthStats(startTimeStamp: Long, endTimeStamp: Long, billID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: Bill month stats
        }
    }
}