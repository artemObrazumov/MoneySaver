package com.borsch_team.moneysaver

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.Room
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.data.api.API
import com.borsch_team.moneysaver.data.databases.MoneySaverDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        moneySaverDatabase = Room.databaseBuilder(
            applicationContext, MoneySaverDatabase::class.java, "moneySaver"
        ).createFromAsset("database/categories.db").build()
        api = API(moneySaverDatabase)
        preferencesManager = PreferencesManager(applicationContext)
    }

    companion object{
        lateinit var instance: App
            private set

        lateinit var moneySaverDatabase: MoneySaverDatabase
            private set

        lateinit var api: API
            private set

        @SuppressLint("StaticFieldLeak")
        lateinit var preferencesManager: PreferencesManager
            private set

        var hasCheckedForPlannedTransactions: Boolean = false
            private set

        var syncUser: Boolean = false
            private set

        fun onTransactionsChecked() {
            hasCheckedForPlannedTransactions = true
        }

        fun onUserSync() {
            syncUser = true
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isOnline(): Boolean {
            val context = instance.applicationContext
            val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =  connManager.getNetworkCapabilities(connManager.activeNetwork)
            return networkCapabilities != null
        }
    }
}