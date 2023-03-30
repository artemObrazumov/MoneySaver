package com.borsch_team.moneysaver.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "billId") var id: Long? = null,
    @ColumnInfo(name = "idType")var idType: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "balance") var balance: Float? = null,
    @ColumnInfo(name = "reservedMoney") var reservedMoney: Float? = null,
)