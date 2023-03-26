package com.borsch_team.moneysaver.ui.auth.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthSignInViewModel: ViewModel() {

    val authResultData: MutableLiveData<AuthResult> = MutableLiveData()

    fun signInWithEmail(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            App.api.signInWithEmail(email, password) {
                authResultData.postValue(it)
            }
        }
    }
}