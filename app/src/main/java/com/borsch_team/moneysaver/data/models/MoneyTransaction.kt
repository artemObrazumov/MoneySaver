package com.borsch_team.moneysaver.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoneyTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId") var id: Long? = null,
    @ColumnInfo(name = "isExpenses") var isExpenses: Boolean? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "date") var date: Long? = null,
    @ColumnInfo(name = "idCategory") var idCategory: Int? = null,
    @ColumnInfo(name = "idBill") var idBill: Int? = null,
    @ColumnInfo(name = "money") var money: Float? = null,
    @ColumnInfo(name = "isPlanned") var isPlanned: Boolean? = null
)