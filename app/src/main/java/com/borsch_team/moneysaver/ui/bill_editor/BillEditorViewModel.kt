package com.borsch_team.moneysaver.ui.bill_editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.Bill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillEditorViewModel: ViewModel() {
    val bill: MutableLiveData<Bill> = MutableLiveData()

    fun getBill(billId: Long) {
        viewModelScope.launch (Dispatchers.IO) {
            bill.postValue(App.api.getBill(billId))
        }
    }

    fun uploadBill(bill: Bill) {
        viewModelScope.launch (Dispatchers.IO) {
            App.api.upsertBill(bill)
        }
    }
}