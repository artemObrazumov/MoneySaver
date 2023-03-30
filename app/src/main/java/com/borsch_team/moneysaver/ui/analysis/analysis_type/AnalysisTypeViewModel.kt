package com.borsch_team.moneysaver.ui.analysis.analysis_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.data.models.AnalysisTransaction
import com.borsch_team.moneysaver.data.models.TransactionAndCategory
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Math.abs

class AnalysisTypeViewModel: ViewModel() {
    var arrExpenses: MutableLiveData<ArrayList<Slice>> = MutableLiveData()
    var arrIncomes: MutableLiveData<ArrayList<Slice>> = MutableLiveData()

    var arrCategories: MutableLiveData<ArrayList<AnalysisTransaction>> = MutableLiveData()


    private fun getDataForPie(arr: List<TransactionAndCategory>): ArrayList<Slice>{
        val hashMapData: HashMap<String, Float> = HashMap()
        val hashMapCategory: HashMap<String, Long> = HashMap()

        arr.forEach {
            if(it.category.name !in hashMapData){
                hashMapData[it.category.name!!] = it.transaction.money!!
                hashMapCategory[it.category.name!!] = it.category.id!!
            }else{
                hashMapData[it.category.name!!] = hashMapData[it.category.name!!]!! + it.transaction.money!!
            }
        }

        val arrData = ArrayList<Slice>()
        hashMapData.forEach {
            arrData.add(Slice(
                kotlin.math.abs(it.value),
                Constants.getColorResource(hashMapCategory[it.key]!!),
                it.key
            ))
        }

        return arrData
    }

    fun getExpenses(startTimestamp: Long, endTimestamp: Long){
        viewModelScope.launch(Dispatchers.IO) {
            arrExpenses.postValue(getDataForPie(App.api.getAllExpensesTransactions(startTimestamp, endTimestamp)))
        }
    }

    fun getIncomes(startTimestamp: Long, endTimestamp: Long){
        viewModelScope.launch(Dispatchers.IO) {
            arrIncomes.postValue(getDataForPie(App.api.getAllIncomesTransactions(startTimestamp, endTimestamp)))
        }
    }

    fun getDataForRecycler(isExpenses: Boolean, startTimestamp: Long, endTimestamp: Long) {

        val dataRecycler: ArrayList<AnalysisTransaction> = ArrayList()
        var data: List<TransactionAndCategory>

        val hashMapData: HashMap<String, Float> = HashMap()
        val hashMapCategory: HashMap<String, Long> = HashMap()

        viewModelScope.launch(Dispatchers.IO) {
            if(isExpenses) {
                data = App.api.getAllExpensesTransactions(startTimestamp, endTimestamp)
            }
            else{
                data = App.api.getAllIncomesTransactions(startTimestamp, endTimestamp)
            }

            data.forEach {
                if(it.category.name !in hashMapData){
                    hashMapData[it.category.name!!] = it.transaction.money!!
                    hashMapCategory[it.category.name!!] = it.category.id!!
                }else{
                    hashMapData[it.category.name!!] = hashMapData[it.category.name!!]!! + it.transaction.money!!
                }
            }
            // Заполняем массив данными
            hashMapData.forEach {
                dataRecycler.add(
                    AnalysisTransaction(
                        it.key,
                        it.value,
                        Constants.getColorResource(hashMapCategory[it.key]!!)
                    )
                )
            }
            if(isExpenses){
                dataRecycler.sortBy { it.money }
            }
            else{
                dataRecycler.sortBy { it.money }
                dataRecycler.reverse()
            }

            arrCategories.postValue(dataRecycler)
        }
    }
}