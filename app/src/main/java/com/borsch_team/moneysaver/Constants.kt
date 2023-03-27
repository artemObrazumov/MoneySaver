package com.borsch_team.moneysaver

import com.borsch_team.moneysaver.ui.adapter.models.BillTypeItem

class Constants {
    companion object {
        const val BILL_TYPE_CARD = 0
        const val BILL_TYPE_BANKNOTES = 1

        const val TRANSACTION_RESULT_SUCCESS = 0
        const val TRANSACTION_RESULT_MONEY_ERROR = 1

        val BILL_TYPES = listOf(
            BillTypeItem(R.drawable.ic_card, "Карта"),
            BillTypeItem(R.drawable.ic_banknotes, "Наличные")
        )
    }
}