package com.borsch_team.moneysaver.data.dao

import androidx.room.*
import com.borsch_team.moneysaver.data.models.Bill

@Dao
interface BillDao{
    @Query("SELECT * FROM bill")
    suspend fun getAllBills(): List<Bill>

    @Query("SELECT * FROM bill WHERE billID = :id")
    suspend fun getBill(id: Long): Bill

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bill: Bill): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(bill: Bill): Int

    @androidx.room.Transaction
    suspend fun upsert(bill: Bill): Long{
        var id = insert(bill)
        if (id == -1L){
            id = update(bill).toLong()
        }
        return id
    }

    @Query("DELETE FROM bill WHERE billID = :id")
    suspend fun delete(id: Long)
    @Query("DELETE FROM bill")
    suspend fun clearBills()
}