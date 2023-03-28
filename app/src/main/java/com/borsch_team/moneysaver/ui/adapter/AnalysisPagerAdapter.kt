package com.borsch_team.moneysaver.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.ui.analysis.analysis_type.AnalysisTypeFragment
import com.borsch_team.moneysaver.ui.transactions.transaction_type.TransactionTypeFragment

class AnalysisPagerAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    private val expensesFragment = AnalysisTypeFragment(true)
    private val incomesFragment = AnalysisTypeFragment(false)

    /*fun updateAdapter(
        billID: Long,
        timeRange: TimeRange
    ) {
        expensesFragment.updateFragment(billID, timeRange)
        incomesFragment.updateFragment(billID, timeRange)
    }*/

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> expensesFragment
            else -> incomesFragment
        }
    }
}
