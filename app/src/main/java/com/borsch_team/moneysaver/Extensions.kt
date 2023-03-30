package com.borsch_team.moneysaver

import androidx.appcompat.app.AppCompatActivity
import com.borsch_team.moneysaver.data.PreferencesManager

fun AppCompatActivity.setupTheme() {
    when (App.preferencesManager.getTheme()) {
        PreferencesManager.THEME_LIGHT -> {
            this.setTheme(R.style.Theme_MoneySaver)
        }
        PreferencesManager.THEME_DARK -> {
            this.setTheme(R.style.Theme_MoneySaver_Dark)
        }
    }
}