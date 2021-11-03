package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.Doctor
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DoctorsRepository
{
    suspend fun getDoctor(id: UUID): Doctor?

    fun getAllDoctors(): Flow<List<Doctor>>

//    suspend fun search(query: String): List<Doctor>

    /**
     * @return Boolean `true` if successful, `false` otherwise
     */
    suspend fun addDoctor(doc: Doctor): Boolean

    /**
     * @return Boolean `true` if successful, `false` otherwise
     */
    suspend fun deleteDoctor(doc: Doctor): Boolean

    /**
     * @return Boolean `true` if successful, `false` otherwise
     */
    suspend fun updateDoctor(doc: Doctor): Boolean
}