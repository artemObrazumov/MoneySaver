package com.borsch_team.moneysaver.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long?,
    @ColumnInfo(name = "is_expenses") var is_expenses: Boolean?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "date") var date: Long?,
    @ColumnInfo(name = "id_category") var id_category: Int?,
    @ColumnInfo(name = "id_bill") var id_bill: Int?,
    @ColumnInfo(name = "money") var money: Float?
)