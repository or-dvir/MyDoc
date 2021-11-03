package com.hotmail.or_dvir.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotmail.or_dvir.database.entities.DoctorEntity.Companion.TABLE_DOCTORS

@Entity(tableName = TABLE_DOCTORS)
data class DoctorEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: String,
    @ColumnInfo(name = "_name")
    val name: String
)
{
    companion object
    {
        const val TABLE_DOCTORS = "Doctors"
    }
}