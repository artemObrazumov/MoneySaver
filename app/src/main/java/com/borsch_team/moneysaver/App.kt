package com.borsch_team.moneysaver

import android.app.Application
import androidx.room.Room
import com.borsch_team.moneysaver.data.databases.MoneySaverDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        moneySaverDatabase = Room.databaseBuilder(
            applicationContext, MoneySaverDatabase::class.java, "moneySaver"
        ).build()
    }

    companion object{
        lateinit var instance: App
            private set

        lateinit var moneySaverDatabase: MoneySaverDatabase
            private set
    }
}