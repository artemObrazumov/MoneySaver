package com.borsch_team.moneysaver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borsch_team.moneysaver.data.models.MoneyTransaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM moneyTransaction WHERE date BETWEEN :startTimeStamp AND :endTimeStamp ORDER BY date")
    suspend fun getAllTransactions(startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM moneyTransaction WHERE isExpenses is 0 AND idBill = :bill AND date BETWEEN :startTimeStamp AND :endTimeStamp ORDER BY date")
    suspend fun getIncomeTransactions(bill: Long, startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM moneyTransaction WHERE isExpenses is 1 AND idBill = :bill AND date BETWEEN :startTimeStamp AND :endTimeStamp ORDER BY date")
    suspend fun getExpensesTransactions(bill: Long, startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM moneyTransaction WHERE transactionId = :id")
    suspend fun getTransaction(id: Long): MoneyTransaction

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moneyTransaction: MoneyTransaction): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(moneyTransaction: MoneyTransaction): Long

    @androidx.room.Transaction
    suspend fun upsert(moneyTransaction: MoneyTransaction): Long{
        var id = insert(moneyTransaction)
        if (id == -1L){
            id = update(moneyTransaction)
        }
        return id
    }

    @Query("DELETE FROM moneyTransaction WHERE transactionId = :id")
    suspend fun delete(id: Long)
}