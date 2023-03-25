package com.borsch_team.moneysaver.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borsch_team.moneysaver.data.dao.BillDao
import com.borsch_team.moneysaver.data.dao.CategoryDao
import com.borsch_team.moneysaver.data.dao.TransactionDao
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TransactionCategory

@Database(entities = [MoneyTransaction::class, Bill::class, TransactionCategory::class], version = 1, exportSchema = true)
abstract class MoneySaverDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun billDao(): BillDao
    abstract fun categoriesDao(): CategoryDao
}