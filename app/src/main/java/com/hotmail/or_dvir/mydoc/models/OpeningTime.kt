package com.hotmail.or_dvir.mydoc.models

import java.text.DateFormat
import java.time.DayOfWeek
import java.util.Calendar

data class OpeningTime(
    val dayOfWeek: DayOfWeek,
    val fromHour: TimeOfDay,
    val toHour: TimeOfDay
)
{
    add validity check (toHour is less than fromHour)

    add function to get user-friendly SHORT string for dayOfWeek
    add function to get user-friendly FULL string for dayOfWeek

    add function to get user-friendly string for fromHour

    add function to get user-friendly string for toHour
}

object OpeningTimeFactory
{
    fun createDefault() = OpeningTime(
        DayOfWeek.MONDAY,
        TimeOfDay(),
        TimeOfDay()
    )
}

class TimeOfDay
{
    init
    {
        Calendar.HOUR_OF_DAY
    }

    check which range the time picker returns!!!! what if the picker is in am/pm mode?
    does it return 10pm or 22?

    add function to get user-friendly SHORT string
        use DateFormat.getTimeInstance(style: int)

    add function to get user-friendly LONG string
    use DateFormat.getTimeInstance(style: int)

    do i need a function for MEDIUM user friendly string?
    do i need a function for FULL user friendly string?

    private companion object
    {
        private val HOUR_RANGE = 0..23
        private val MINUTE_RANGE = 0..59

        private val HOUR_DEFAULT = HOUR_RANGE.first
        private val MINUTE_DEFAULT = MINUTE_RANGE.first
    }

    var hour: Int = HOUR_DEFAULT
        set(value)
        {
            field = value.takeIf { it in HOUR_RANGE } ?: HOUR_DEFAULT
        }

    var minute: Int = MINUTE_DEFAULT
        set(value)
        {
            field = value.takeIf { it in MINUTE_RANGE } ?: MINUTE_DEFAULT
        }
}