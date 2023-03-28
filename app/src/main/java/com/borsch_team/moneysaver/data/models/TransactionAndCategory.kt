package com.borsch_team.moneysaver.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionAndCategory (
    @Embedded
    var transaction: MoneyTransaction,
    @Relation(parentColumn = "idCategory", entityColumn = "categoryId")
    var category: TransactionCategory
)