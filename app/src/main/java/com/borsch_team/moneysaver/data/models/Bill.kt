package com.borsch_team.moneysaver.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "billId") var id: Long?,
    @ColumnInfo(name = "idType")var id_type: Int?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "balance") var balance: Float?,
)