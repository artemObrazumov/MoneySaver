package com.borsch_team.moneysaver.data.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseAPI {
    companion object {
        suspend fun getLastUpdateTime(): Long {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                return -1L
            } else {
                FirebaseDatabase.getInstance()
                    .getReference("userLastUpdate/${user.uid}").get().await()
            }
            return -1L
        }
    }
}