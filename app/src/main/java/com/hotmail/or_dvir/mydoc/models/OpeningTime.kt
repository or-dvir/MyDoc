package com.hotmail.or_dvir.mydoc.models

import com.hotmail.or_dvir.mydoc.ui.shared.atLeastOneNull
import com.hotmail.or_dvir.mydoc.ui.shared.formatShort
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

data class OpeningTime(
    //todo should all variables be nullable???
    val dayOfWeek: DayOfWeek,
    val fromHour: LocalTime,
    val toHour: LocalTime
)
{
    fun isValid(): Boolean
    {
        return if (atLeastOneNull(fromHour, toHour))
        {
            //fromHour and toHour are not mandatory
            true
        } else
        {
            fromHour.isAfter(toHour)
        }
    }

    //todo check the output (for for 24 and 12 hours
    fun getFromHourShort() = fromHour.formatShort()
    fun getToHourShort() = toHour.formatShort()

    fun getDayOfWeekShort() = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    //todo check output. try different text styles
    fun getDayOfWeekLong() = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    //todo
    // do i need getFromHourLong()??
    // check FULL VS LONG VS MEDIUM

    //todo
    // check which range the time picker returns!!!! what if the picker is in am/pm mode?
    // does it return 10pm or 22?
}

object OpeningTimeFactory
{
    fun createDefault() = OpeningTime(
        dayOfWeek = DayOfWeek.MONDAY,
        fromHour = LocalTime.MIN,
        toHour = LocalTime.MAX
    )
}