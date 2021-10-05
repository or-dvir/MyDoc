package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.Doctor
import java.util.UUID

interface DoctorsRepository : BaseRepository<Doctor>
{
    suspend fun getDoctor(id: UUID): Doctor
}