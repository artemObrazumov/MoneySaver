package com.borsch_team.moneysaver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borsch_team.moneysaver.data.models.MoneyTransaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM tran")
    fun getAllTransactions(startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM tran WHERE isExpenses is FALSE")
    fun getIncomeTransactions(startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM tran WHERE isExpenses is TRUE")
    fun getExpensesTransactions(startTimeStamp: Long, endTimeStamp: Long): List<MoneyTransaction>

    @Query("SELECT * FROM tran WHERE transactionId = :id")
    fun getTransaction(id: Long): MoneyTransaction

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(moneyTransaction: MoneyTransaction): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun update(moneyTransaction: MoneyTransaction): Long

    @androidx.room.Transaction
    fun upsert(moneyTransaction: MoneyTransaction): Long{
        var id = insert(moneyTransaction)
        if (id == -1L){
            id = update(moneyTransaction)
        }
        return id
    }

    @Query("DELETE FROM tran WHERE transactionId = :id")
    fun delete(id: Long)
}