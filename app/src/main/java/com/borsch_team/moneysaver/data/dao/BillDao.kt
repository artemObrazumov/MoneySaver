package com.borsch_team.moneysaver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borsch_team.moneysaver.data.models.Bill

@Dao
interface BillDao{
    @Query("SELECT * FROM bill")
    fun getAllBills(): List<Bill>

    @Query("SELECT * FROM bill WHERE billID = :id")
    fun getBill(id: Long): Bill

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bill: Bill): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun update(bill: Bill): Long

    @androidx.room.Transaction
    fun upsert(bill: Bill): Long{
        var id = insert(bill)
        if (id == -1L){
            id = update(bill)
        }
        return id
    }

    @Query("DELETE FROM bill WHERE billID = :id")
    fun delete(id: Long)
}