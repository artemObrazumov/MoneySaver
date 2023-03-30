package com.borsch_team.moneysaver.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borsch_team.moneysaver.Utils
import com.borsch_team.moneysaver.data.models.TimeRange

class AnalysisViewModel : ViewModel() {
    val mutableTimeRange: MutableLiveData<TimeRange> = MutableLiveData()

    init {
        getTimeRangeForInit()
    }

    private fun getTimeRangeForInit(){
        mutableTimeRange.value = TimeRange(Utils.getFirstDayMonth(), Utils.getLastDayMonth())
    }
}