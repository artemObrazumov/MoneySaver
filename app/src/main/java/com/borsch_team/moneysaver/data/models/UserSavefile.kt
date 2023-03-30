package com.borsch_team.moneysaver.data.models

data class UserSavefile(
    var name: String = "",
    var bills: List<Bill> = emptyList(),
    var categories: List<TransactionCategory> = emptyList(),
    var transactions: List<MoneyTransaction> = emptyList(),
)