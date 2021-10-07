package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.AddressFactory
import com.hotmail.or_dvir.mydoc.models.Doctor
import com.hotmail.or_dvir.mydoc.models.DoctorFactory
import java.util.UUID

class DoctorsRepositoryImpl : DoctorsRepository
{
    override suspend fun getDoctor(id: UUID): Doctor
    {
        //TODO("not implemented")
        return DoctorFactory.getDummyDoctor()
    }

    override suspend fun getAll(): List<Doctor>
    {
        //TODO("not implemented")
        return List(50) { index ->
            Doctor(
                UUID.randomUUID(),
                "Dr. $index",
                "special $index",
                AddressFactory.getDummyAddress()
            )
        }
    }

    override suspend fun search(searchQuery: String): List<Doctor>
    {
        //TODO("not implemented")
        return listOf()
    }

    override suspend fun add(obj: Doctor): Boolean
    {
        //TODO("not implemented")
        return true
    }

    override suspend fun delete(obj: Doctor): Boolean
    {
        //TODO("not implemented")
        return true
    }

    override suspend fun update(obj: Doctor): Boolean
    {
        //TODO("not implemented")
        return true
    }
}