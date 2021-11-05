package com.hotmail.or_dvir.mydoc.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity.Companion.TABLE_DOCTORS
import com.hotmail.or_dvir.mydoc.models.SimpleAddress

@Entity(tableName = TABLE_DOCTORS)
data class DoctorEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_SPECIALITY)
    val speciality: String?,
    @Embedded
    val address: SimpleAddress?
)
{
    check all combinations of address object to see what it looks like in the database

    companion object
    {
        const val TABLE_DOCTORS = "Doctors"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "_name"
        const val COLUMN_SPECIALITY = "_speciality"
    }
}