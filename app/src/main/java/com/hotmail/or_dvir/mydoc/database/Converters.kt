package com.hotmail.or_dvir.mydoc.database

import androidx.room.TypeConverter
import com.hotmail.or_dvir.mydoc.models.OpeningTime

class Converters
{
    @TypeConverter
    fun fromOpeningTimeList(value: List<OpeningTime>?): String?
    {
        return value?.let {
            OpeningTime.moshiListAdapter.toJson(value)
        }
    }

    @TypeConverter
    fun toOpeningTimeList(json: String?): List<OpeningTime>?
    {
        return json?.let {
            OpeningTime.moshiListAdapter.fromJson(json)
        }
    }
}