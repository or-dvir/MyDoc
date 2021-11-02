package com.hotmail.or_dvir.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hotmail.or_dvir.database.DoctorEntity
import com.hotmail.or_dvir.database.DoctorEntity.Companion.TABLE_DOCTORS
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorsDao
{
    //returns the new rowId of the inserted item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: DoctorEntity): Int

    //returns the number of rows that were deleted
    @Delete
    suspend fun delete(doctor: DoctorEntity): Int

    //returns the number of rows that were updated
    @Update
    suspend fun update(doctor: DoctorEntity): Int

    @Query("select * from $TABLE_DOCTORS")
    fun getAllDoctors(): Flow<DoctorEntity>
}