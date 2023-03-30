package com.borsch_team.moneysaver

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object{
        fun getActualData(): Long {
            val c = Calendar.getInstance()
            return c.time.time
        }

        fun getFirstDayMonth(): Long {
            val c = Calendar.getInstance()

            c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH))
            c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY))
            c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE))
            c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND))
            c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND))

            return c.time.time
        }

        fun getLastDayMonth(): Long {
            val c = Calendar.getInstance()

            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH))
            c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY))
            c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE))
            c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND))
            c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND))

            return c.time.time
        }

        fun getStringDate(date: Long): String{
            return SimpleDateFormat(Constants.TIME_FORMAT_PATTERN, Locale("ru")).format(date)
        }
    }
}