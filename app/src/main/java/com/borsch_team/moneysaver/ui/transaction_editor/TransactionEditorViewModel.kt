package com.borsch_team.moneysaver.ui.transaction_editor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionEditorViewModel: ViewModel() {
    val transactionSendResult: MutableLiveData<Int> = MutableLiveData()

    fun trySendTransaction(transaction: MoneyTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            val billBalance = App.api.getBill(transaction.idBill!!.toLong()).balance!!
            if (billBalance + transaction.money!! < 0) {
                transactionSendResult.postValue(Constants.TRANSACTION_RESULT_MONEY_ERROR)
            } else {
                transactionSendResult.postValue(Constants.TRANSACTION_RESULT_SUCCESS)
                upsertTransaction(transaction)
            }
        }
    }

    private fun upsertTransaction(transaction: MoneyTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            App.api.upsertTransaction(transaction)
        }
    }
}