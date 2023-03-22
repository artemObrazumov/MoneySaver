package com.borsch_team.moneysaver.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.borsch_team.moneysaver.ui.bill_slide.BillSlideFragment

class BillsAdapter(
    activity: FragmentActivity,
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5
    override fun createFragment(position: Int): Fragment = BillSlideFragment()
}