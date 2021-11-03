package com.hotmail.or_dvir.mydoc.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity.Companion.COLUMN_ID
import com.hotmail.or_dvir.mydoc.database.entities.DoctorEntity.Companion.TABLE_DOCTORS
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorsDao
{
    /**
     * @return the new rowId of the inserted item, or -1 if the operation failed
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: DoctorEntity): Long

    /**
     * @return the number of rows that were deleted
     */
    @Delete
    suspend fun delete(doctor: DoctorEntity): Int

    /**
     * @return the number of rows that were updated
     */
    @Update
    suspend fun update(doctor: DoctorEntity): Int

    @Query("select * from $TABLE_DOCTORS where $COLUMN_ID = :id limit 1")
    suspend fun getDoctor(id: String): DoctorEntity?

    @Query("select * from $TABLE_DOCTORS")
    fun getAllDoctors(): Flow<List<DoctorEntity>>
}