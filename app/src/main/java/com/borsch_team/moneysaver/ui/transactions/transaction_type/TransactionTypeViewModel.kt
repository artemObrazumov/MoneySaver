package com.borsch_team.moneysaver.ui.transactions.transaction_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TimeRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionTypeViewModel: ViewModel() {
    val arrExpenses: MutableLiveData<List<MoneyTransaction>> = MutableLiveData()
    val arrIncome: MutableLiveData<List<MoneyTransaction>> = MutableLiveData()

//    fun loadExpenses(){
//        viewModelScope.launch(Dispatchers.IO){
//            val data = App.api.getExpensesTransactions(
//                billID,
//                timeRange.startTimestamp,
//                timeRange.endTimestamp
//            )
//            arrExpenses.postValue(data)
//        }
//    }
//    fun loadIncome(){
//        viewModelScope.launch(Dispatchers.IO){
//            val data = App.api.getIncomeTransactions()
//            arrIncome.postValue(data)
//        }
//    }

    fun loadSpecifiedExpenses(billID: Long, timeRange: TimeRange) {
        viewModelScope.launch(Dispatchers.IO) {
            arrExpenses.postValue(App.api.getExpensesTransactions(
                billID,
                timeRange.startTimestamp,
                timeRange.endTimestamp))
        }
    }

    fun loadSpecifiedIncomes(billID: Long, timeRange: TimeRange) {
        viewModelScope.launch(Dispatchers.IO) {
            arrExpenses.postValue(App.api.getIncomesTransactions(
                billID,
                timeRange.startTimestamp,
                timeRange.endTimestamp))
        }
    }
}