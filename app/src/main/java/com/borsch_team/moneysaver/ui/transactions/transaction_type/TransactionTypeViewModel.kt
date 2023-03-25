package com.borsch_team.moneysaver.ui.transactions.transaction_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.api.API
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionTypeViewModel: ViewModel() {
    val arrExpenses: MutableLiveData<ArrayList<MoneyTransaction>> = MutableLiveData()
    val arrIncome: MutableLiveData<ArrayList<MoneyTransaction>> = MutableLiveData()

    fun loadExpenses(){
        viewModelScope.launch(Dispatchers.IO){
            val data = App.api.getExpensesTransactions()
            arrExpenses.postValue(data)
        }
    }
    fun loadIncome(){
        viewModelScope.launch(Dispatchers.IO){
            val data = App.api.getIncomeTransactions()
            arrIncome.postValue(data)
        }
    }

}