package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.database.daos.DoctorsDao
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.other.toDoctor
import com.hotmail.or_dvir.mydoc.other.toDoctorEntity
import com.hotmail.or_dvir.mydoc.other.toDoctors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

class DoctorsRepositoryImpl(private val doctorsDao: DoctorsDao) : DoctorsRepository
{
    private companion object
    {
        private val IoDispatcher = Dispatchers.IO
    }

    override suspend fun getDoctor(id: UUID): Doctor?
    {
        return withContext(IoDispatcher) {
            //todo check if non existent id indeed returns null or something else
            // maybe i need a list of length 1 here?
            doctorsDao.getDoctor(id.toString())?.toDoctor()
        }
    }

    override fun getAllDoctors(): Flow<List<Doctor>> =
        doctorsDao.getAllDoctors().map { it.toDoctors() }

    override suspend fun addDoctor(doc: Doctor): Boolean
    {
        return withContext(IoDispatcher) {
            val rowId = doctorsDao.insert(doc.toDoctorEntity())
            rowId != -1L
        }
    }

    override suspend fun deleteDoctor(doc: Doctor): Boolean
    {
        return withContext(IoDispatcher) {
            val rowsDeleted = doctorsDao.delete(doc.toDoctorEntity())
            rowsDeleted == 1
        }
    }
}