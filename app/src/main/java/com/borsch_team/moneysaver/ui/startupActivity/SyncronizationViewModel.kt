package com.borsch_team.moneysaver.ui.startupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.api.API
import com.borsch_team.moneysaver.data.api.FirebaseAPI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class SyncronizationViewModel: ViewModel() {
    val finished: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        if (!App.hasCheckedForPlannedTransactions) {
            App.onTransactionsChecked()
            checkForPlannedTransactions()
        }

        if (!App.syncUser) {
            App.onUserSync()
            syncUser()
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

    private fun syncUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!App.isOnline()) {
                finished.postValue(true)
                return@launch
            }
            val user = FirebaseAuth.getInstance().currentUser
            var userId = ""
            if (user == null) {
                finished.postValue(true)
                return@launch
            } else {
                userId = user.uid
            }

            val lastTimeUpdateDB = FirebaseAPI.getLastUpdateTime()
            val lastTimeUpdateDevice = App.preferencesManager.getLastTimeUpdate()
            val currentTime = Calendar.getInstance().timeInMillis

            if (lastTimeUpdateDB == -1L && lastTimeUpdateDevice == -1L) {
                FirebaseAPI.saveUserData(currentTime)
                finished.postValue(true)
            } else if (lastTimeUpdateDB > lastTimeUpdateDevice) {
                App.api.reloadUserData(FirebaseAPI.loadUserData())
                finished.postValue(true)
            } else {
                FirebaseAPI.saveUserData(currentTime)
                finished.postValue(true)
            }
        }
    }
}