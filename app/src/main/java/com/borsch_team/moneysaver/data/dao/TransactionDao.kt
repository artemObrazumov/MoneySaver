package com.borsch_team.moneysaver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borsch_team.moneysaver.data.models.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transaction")
    fun getAllTransactions(startTimeStamp: Long, endTimeStamp: Long): List<Transaction>

    @Query("SELECT * FROM transaction WHERE is_expenses is FALSE")
    fun getIncomeTransactions(startTimeStamp: Long, endTimeStamp: Long): List<Transaction>

    @Query("SELECT * FROM transaction WHERE is_expenses is TRUE")
    fun getExpensesTransactions(startTimeStamp: Long, endTimeStamp: Long): List<Transaction>

    @Query("SELECT * FROM transaction WHERE id = :id")
    fun getTransaction(id: Long): Transaction

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: Transaction): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun update(transaction: Transaction): Int

    @androidx.room.Transaction
    fun upsert(transaction: Transaction): Long{
        var id = insert(transaction)
        if (id == -1L){
            id =  update(transaction).toLong()
        }
        return id
    }

    @Query("DELETE FROM transaction WHERE id = :id")
    fun delete(id: Long)
}