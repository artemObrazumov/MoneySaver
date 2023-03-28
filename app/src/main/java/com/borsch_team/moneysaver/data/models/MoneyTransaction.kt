package com.borsch_team.moneysaver.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoneyTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId") var id: Long?,
    @ColumnInfo(name = "isExpenses") var isExpenses: Boolean?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "date") var date: Long?,
    @ColumnInfo(name = "idCategory") var idCategory: Int?,
    @ColumnInfo(name = "idBill") var idBill: Int?,
    @ColumnInfo(name = "money") var money: Float?,
    @ColumnInfo(name = "isPlanned") var isPlanned: Boolean?
)