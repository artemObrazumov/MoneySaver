package com.borsch_team.moneysaver.ui.analysis.analysis_type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.data.models.TransactionAndCategory
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.faskn.lib.PieChart
import com.faskn.lib.Slice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalysisTypeViewModel: ViewModel() {
    var arrExpenses: MutableLiveData<ArrayList<Slice>> = MutableLiveData()
    var arrIncomes: MutableLiveData<ArrayList<Slice>> = MutableLiveData()

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
                it.value,
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
}