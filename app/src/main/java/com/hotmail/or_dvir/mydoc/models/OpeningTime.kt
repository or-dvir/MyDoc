package com.hotmail.or_dvir.mydoc.models

import com.hotmail.or_dvir.mydoc.moshi.MyMoshi
import com.hotmail.or_dvir.mydoc.ui.shared.formatLong
import com.hotmail.or_dvir.mydoc.ui.shared.formatShort
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types
import java.time.DayOfWeek
import java.time.LocalTime

@JsonClass(generateAdapter = true)
data class OpeningTime(
    @Json(name = "_dayOfWeek")
    val dayOfWeek: DayOfWeek,
    @Json(name = "_fromHour")
    val fromHour: LocalTime,
    @Json(name = "_toHour")
    val toHour: LocalTime
)
{
    companion object
    {
        private var listType = Types.newParameterizedType(
            List::class.java,
            OpeningTime::class.java
        )

        var moshiListAdapter: JsonAdapter<List<OpeningTime>> =
            MyMoshi.instance.adapter(listType)

        fun getAllDaysOfWeekShort() = DayOfWeek.values().map { it.formatShort() }
    }

    fun isValid() = fromHour.isAfter(toHour)

    //todo check the output (for for 24 and 12 hours)
    fun getFromHourShort() = fromHour.formatShort()
    fun getToHourShort() = toHour.formatShort()
    fun getDayOfWeekShort() = dayOfWeek.formatShort()

    //todo check output. try different text styles
    fun getDayOfWeekLong() = dayOfWeek.formatLong()

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