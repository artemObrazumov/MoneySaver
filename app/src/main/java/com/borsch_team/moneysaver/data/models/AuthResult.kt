package com.borsch_team.moneysaver.data.models

import com.google.firebase.auth.FirebaseUser

data class AuthResult(
    val isSuccessful: Boolean = true,
    val user: FirebaseUser?,
    val error: String,
)