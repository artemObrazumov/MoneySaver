package com.borsch_team.moneysaver

import com.borsch_team.moneysaver.ui.adapter.models.BillTypeItem

class Constants {
    companion object {
        const val BILL_TYPE_CARD = 0
        const val BILL_TYPE_BANKNOTES = 1

        const val TRANSACTION_RESULT_SUCCESS = 0
        const val TRANSACTION_RESULT_MONEY_ERROR = 1

        const val TIME_FORMAT_PATTERN = "HH:mm, dd MMMM yyyy"

        val BILL_TYPES = listOf(
            BillTypeItem(R.drawable.ic_card, "Карта"),
            BillTypeItem(R.drawable.ic_banknotes, "Наличные")
        )


        fun getCategoryImageResource(categoryId: Long): Int =
            when (categoryId) {
                1L -> R.drawable.cart_svgrepo_com
                2L -> R.drawable.fashion_svgrepo_com
                3L -> R.drawable.flower_svgrepo_com
                4L -> R.drawable.brush_pencil_svgrepo_com
                5L -> R.drawable.clipboard_svgrepo_com
                6L -> R.drawable.cat_icon
                7L -> R.drawable.car_svgrepo_com
                8L -> R.drawable.mail_svgrepo_com
                9L -> R.drawable.pills_pill_svgrepo_com
                10L -> R.drawable.restaurant_plate_svgrepo_com
                11L -> R.drawable.arrow_down_svgrepo_com
                12L -> R.drawable.money_svgrepo_com
                13L -> R.drawable.zoomout_svgrepo_com
                14L -> R.drawable.briefcase_svgrepo_com
                15L -> R.drawable.arrow_down_svgrepo_com
                16L -> R.drawable.money_svgrepo_com
                17L -> R.drawable.zoomin_svgrepo_com
                18L -> R.drawable.booklet_svgrepo_com
                else -> R.drawable.ellipsis_6ngbbouw69zs
            }
        fun getColorResource(categoryId: Long): Int =
            when (categoryId) {
                1L -> R.color.products
                2L -> R.color.clothes
                3L -> R.color.for_home
                4L -> R.color.hobby
                5L -> R.color.subscribe
                6L -> R.color.pets
                7L -> R.color.traunsport
                8L -> R.color.bills
                9L -> R.color.health
                10L -> R.color.restauran
                11L -> R.color.perevodi
                12L -> R.color.get_cash
                13L -> R.color.other_expenses
                14L -> R.color.education
                15L -> R.color.earn
                16L -> R.color.perevodi
                17L -> R.color.get_cash
                18L -> R.color.other_earns
                else -> R.color.other_category
            }
    }
}
