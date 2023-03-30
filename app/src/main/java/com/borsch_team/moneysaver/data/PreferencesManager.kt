package com.borsch_team.moneysaver.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager {
    companion object {
        private const val SAVEFILE = "money_saver"

        private const val SELECTED_THEME = "selected_theme"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_OLED = 2

        private const val USER_NAME = "user_name"

        fun saveTheme(newTheme: Int, context: Context) {
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).edit().putInt(SELECTED_THEME, newTheme).apply()
        }

        fun getTheme(context: Context) =
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).getInt(SELECTED_THEME, THEME_LIGHT)

        fun saveUsername(name: String, context: Context) {
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).edit().putString(USER_NAME, name).apply()
        }

        fun getUsername(context: Context) =
            context.getSharedPreferences(
                SAVEFILE, Context.MODE_PRIVATE
            ).getInt(USER_NAME, THEME_LIGHT)
    }
}