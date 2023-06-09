package com.borsch_team.moneysaver.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.TimeRange
import com.borsch_team.moneysaver.ui.transactions.transaction_type.TransactionTypeFragment

class TransactionPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onCreated: () -> Unit,
    private val onTransactionRemoved: () -> Unit
): FragmentStateAdapter(fragmentActivity) {

    private var expensesFragmentInitialized = false
    private var incomesFragmentInitialized = false

    private var expensesFragment = TransactionTypeFragment(true, {
        expensesFragmentInitialized = true
        if (incomesFragmentInitialized) {
            onCreated()
        }
    }) {
        onTransactionRemoved()
    }
    private var incomesFragment = TransactionTypeFragment(false, {
        incomesFragmentInitialized = true
        if (expensesFragmentInitialized) {
            onCreated()
        }
    }) {
        onTransactionRemoved()
    }

    fun updateAdapter(
        billID: Long,
        timeRange: TimeRange
    ) {
        expensesFragment.updateFragment(billID, timeRange)
        incomesFragment.updateFragment(billID, timeRange)
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> expensesFragment
            else -> incomesFragment
        }
    }
}
