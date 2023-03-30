package com.borsch_team.moneysaver.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.ui.adapter.models.BillTypeItem

class BillTypeAdapter (
    private var context: Context,
    private var billTypes: List<BillTypeItem>
): BaseAdapter() {
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = billTypes.size

    override fun getItem(i: Int): Any? = null

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val spinnerView = inflater.inflate(R.layout.bill_type_item,null)
        val icon = spinnerView.findViewById<View>(R.id.imageView) as ImageView?
        val names = spinnerView.findViewById<View>(R.id.tv_date) as TextView?
        icon!!.setImageResource(billTypes[i].image)
        names!!.text = billTypes[i].textContent
        return spinnerView
    }
}