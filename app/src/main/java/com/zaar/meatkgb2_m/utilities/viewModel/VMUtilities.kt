package com.zaar.meatkgb2_m.utilities.viewModel

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class VMUtilities {
    fun diffBetweenDates(dateFormat: SimpleDateFormat, firstDay: String?, lastDay: String?): Long {
        val diffMilliseconds: Long =
            ((lastDay?.let { getEpochMilisec(it, dateFormat) })
                ?: 0L).minus((firstDay?.let { getEpochMilisec(it, dateFormat) } ?: 0L))
        if (diffMilliseconds >= 0) {
            //diffMilliseconds -> to sec -> to min -> to hour -> to days
            return diffMilliseconds.div(1000).div(60).div(60).div(24)
        }
        return 0L
    }

    fun getEpochMilisec(incomeDate: String, dateFormat: SimpleDateFormat): Long {
        return dateFormat.parse(incomeDate)?.time ?: 0L
    }

    fun convertDateServToLocal(servDate: String): String?{
        val servDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val localDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date: Date? = servDateFormat.parse(servDate)
        return if (date != null) {
            localDateFormat.format(date)
        } else null
    }
}