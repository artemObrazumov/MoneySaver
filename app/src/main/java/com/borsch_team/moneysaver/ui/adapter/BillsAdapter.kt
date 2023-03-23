package com.borsch_team.moneysaver.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.borsch_team.moneysaver.ui.bill_slide.BillLastSlideFragment
import com.borsch_team.moneysaver.ui.bill_slide.BillSlideFragment

class BillsAdapter(
    activity: FragmentActivity,
    private val onNewBillClicked: () -> Unit
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 1
    override fun createFragment(position: Int): Fragment =
        if (position < 0) {
            BillSlideFragment()
        } else {
            BillLastSlideFragment {
                onNewBillClicked()
            }
        }
}