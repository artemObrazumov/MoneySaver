package com.borsch_team.moneysaver.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager {
    companion object {
        const val SAVEFILE = "money_saver"

        const val SELECTED_THEME = "selected_theme"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_OLED = 2

        fun saveTheme(newTheme: Int, context: Context) {
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).edit().putInt(SELECTED_THEME, newTheme).apply()
        }

        fun getTheme(context: Context) =
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).getInt(SELECTED_THEME, THEME_LIGHT)
    }
}