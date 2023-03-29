package com.borsch_team.moneysaver.ui.bill_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.TimeRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class BillDetailViewModel: ViewModel() {
    val monthStats: MutableLiveData<BillMonthStats> = MutableLiveData()
    val bill: MutableLiveData<Bill> = MutableLiveData()

    fun getBillMonthStats(timeRange: TimeRange, billID: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            var incomesSum = 0f
            App.api.getIncomesTransactions(billID,
                timeRange.startTimestamp, timeRange.endTimestamp)
                .forEach {
                    incomesSum += abs(it.transaction.money!!)
                }
            var expensesSum = 0f
            App.api.getExpensesTransactions(billID,
                timeRange.startTimestamp, timeRange.endTimestamp)
                .forEach {
                    expensesSum += abs(it.transaction.money!!)
                }
            monthStats.postValue(BillMonthStats(expensesSum, incomesSum))
        }
    }

    fun loadBill(billId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            bill.postValue(App.api.getBill(billId))
        }
    }
}