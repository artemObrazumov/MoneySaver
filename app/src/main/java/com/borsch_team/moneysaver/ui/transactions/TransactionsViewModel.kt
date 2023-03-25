package com.borsch_team.moneysaver.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.Bill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {

    val bills: MutableLiveData<List<Bill>> = MutableLiveData()

    fun getBills() {
        viewModelScope.launch(Dispatchers.IO) {
            bills.postValue(App.api.getBills())
        }
    }
}