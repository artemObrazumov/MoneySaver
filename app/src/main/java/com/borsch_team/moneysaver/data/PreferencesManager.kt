package com.borsch_team.moneysaver.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(private val context: Context) {

    fun getLastTimeUpdate() =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).getLong(LAST_TIME_UPDATE, -1L)

    fun saveLastTimeUpdate(time: Long) =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).edit().putLong(LAST_TIME_UPDATE, time).apply()

    fun saveTheme(newTheme: Int) {
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).edit().putInt(SELECTED_THEME, newTheme).apply()
    }

    fun getTheme() =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).getInt(SELECTED_THEME, THEME_LIGHT)

    fun saveUsername(name: String) {
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).edit().putString(USER_NAME, name).apply()
    }

    fun getUsername() =
        context.getSharedPreferences(
            SAVEFILE, Context.MODE_PRIVATE
        ).getString(USER_NAME, "")

    companion object {
        private const val SAVEFILE = "money_saver"

        private const val SELECTED_THEME = "selected_theme"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_OLED = 2

        private const val USER_NAME = "user_name"
        private const val LAST_TIME_UPDATE = "last_time_update"
    }
}