package com.borsch_team.moneysaver.ui.bill_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.TimeRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillDetailViewModel: ViewModel() {
    val monthStats: MutableLiveData<BillMonthStats> = MutableLiveData()
    val bill: MutableLiveData<Bill> = MutableLiveData()

    fun getBillMonthStats(timeRange: TimeRange, billID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: Bill month stats
        }
    }

    fun loadBill(billId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            bill.postValue(App.api.getBill(billId))
        }
    }
}