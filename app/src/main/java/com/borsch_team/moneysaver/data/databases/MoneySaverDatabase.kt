package com.borsch_team.moneysaver.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borsch_team.moneysaver.data.dao.BillDao
import com.borsch_team.moneysaver.data.dao.TransactionDao
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.MoneyTransaction

@Database(entities = [MoneyTransaction::class, Bill::class], version = 1, exportSchema = true)
abstract class MoneySaverDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun billDao(): BillDao
}