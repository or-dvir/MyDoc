package com.hotmail.or_dvir.mydoc.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import java.time.LocalTime

object MyMoshi
{
    val instance = Moshi.Builder()
        .add(LocalTimeAdapter())
        .build()
}

class LocalTimeAdapter
{
    @ToJson
    fun toJson(time: LocalTime): String
    {
        return "${time.hour}:${time.minute}"
    }

    @FromJson
    fun fromJson(timeStr: String): LocalTime
    {
        return timeStr.split(":").let {
            LocalTime.of(it[0].toInt(), it[1].toInt())
        }
    }
}