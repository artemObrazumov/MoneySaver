package com.borsch_team.moneysaver.data.api

import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.models.UserSavefile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseAPI {
    companion object {
        suspend fun getLastUpdateTime(): Long {
            val user = FirebaseAuth.getInstance().currentUser ?: return -1L
            val snapshot = FirebaseDatabase.getInstance()
                .getReference("userLastUpdate/${user.uid}").get().await()
            if (!snapshot.exists()) return -1L
            return snapshot.getValue(Long::class.java)!!
        }

        suspend fun updateLastTimeUpdation(time: Long) {
            val user = FirebaseAuth.getInstance().currentUser ?: return
            FirebaseDatabase.getInstance().getReference("userLastUpdate/${user.uid}")
                .setValue(time).await()
        }

        suspend fun saveUserData(currentTime: Long) {
            FirebaseDatabase.getInstance()
                .getReference("userSaves/${FirebaseAuth.getInstance().currentUser!!.uid}")
                .setValue(App.api.formUserSavefile()).await()
            FirebaseDatabase.getInstance()
                .getReference("userLastUpdate/${FirebaseAuth.getInstance().currentUser!!.uid}")
                .setValue(currentTime).await()
        }

        suspend fun loadUserData() =
            FirebaseDatabase.getInstance()
                .getReference("userSaves/${FirebaseAuth.getInstance().currentUser!!.uid}")
                .get().await().getValue(UserSavefile::class.java)!!
    }
}