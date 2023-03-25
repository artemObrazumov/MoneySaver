package com.borsch_team.moneysaver.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TransactionCategory (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    var id: Long? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "isExpenses")
    var isExpenses: Boolean? = null
): Parcelable