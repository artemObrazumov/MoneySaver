package com.borsch_team.moneysaver.ui.auth.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthSignUpViewModel: ViewModel() {
    val authResultData: MutableLiveData<AuthResult> = MutableLiveData()

    fun signUpWithEmail(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            App.api.signUpWithEmail(email, password) {
                authResultData.postValue(it)
            }
        }
    }
}