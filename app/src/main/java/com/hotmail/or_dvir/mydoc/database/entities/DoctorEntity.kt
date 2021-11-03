package com.hotmail.or_dvir.mydoc.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity.Companion.TABLE_DOCTORS

@Entity(tableName = TABLE_DOCTORS)
data class DoctorEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String
)
{
    companion object
    {
        const val TABLE_DOCTORS = "Doctors"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "_name"
    }
}