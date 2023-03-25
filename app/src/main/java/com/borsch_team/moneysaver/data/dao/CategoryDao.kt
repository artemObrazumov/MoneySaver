package com.borsch_team.moneysaver.data.dao

import androidx.room.*
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.data.models.TransactionCategory

@Dao
interface CategoryDao{
    @Query("SELECT * FROM transactionCategory")
    suspend fun getAllCategories(): List<TransactionCategory>

    @Query("SELECT * FROM transactionCategory WHERE isExpenses = :isExpenses")
    suspend fun getSpecificCategories(isExpenses: Boolean): List<TransactionCategory>

    @Query("SELECT * FROM transactionCategory WHERE categoryId = :id")
    suspend fun getCategory(id: Long): TransactionCategory

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: TransactionCategory): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(category: TransactionCategory): Int

    @Transaction
    suspend fun upsert(category: TransactionCategory): Long {
        var id = insert(category)
        if (id == -1L){
            id = update(category).toLong()
        }
        return id
    }

    @Query("DELETE FROM transactionCategory WHERE categoryId = :id")
    suspend fun delete(id: Long)
}