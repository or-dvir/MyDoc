package com.hotmail.or_dvir.mydoc.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity.Companion.TABLE_DOCTORS
import com.hotmail.or_dvir.mydoc.models.ContactDetails
import com.hotmail.or_dvir.mydoc.models.OpeningTime
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
    val address: SimpleAddress?,
    @Embedded
    val contactDetails: ContactDetails?,
    @ColumnInfo(name = COLUMN_OPENING_TIMES)
    val openingTimes: List<OpeningTime>,
)
{
    //todo
    // is this class getting too big? too many columns in the database?
    // should probably make separate tables (do i need relations? could get complicated!!!)
    // in doctors list, any information we don't display to the user is unnecessarily extracted...
    //      address, contact details, what else?

    companion object
    {
        const val TABLE_DOCTORS = "Doctors"

        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "_name"
        const val COLUMN_SPECIALITY = "_speciality"
        const val COLUMN_OPENING_TIMES = "_openingTimes"
    }
}