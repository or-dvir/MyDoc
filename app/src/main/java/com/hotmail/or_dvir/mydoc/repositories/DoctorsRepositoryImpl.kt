package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.Address
import com.hotmail.or_dvir.mydoc.models.Doctor
import java.util.UUID

class DoctorsRepositoryImpl : DoctorsRepository
{
    private val dummyAddress = Address("bla", "4")

    private val dummyDoctor = Doctor(
        UUID.randomUUID(),
        "Dr Evil",
        "evilness",
        dummyAddress
    )

    override suspend fun getDoctor(id: UUID): Doctor
    {
        //TODO("not implemented")
        return dummyDoctor
    }

    override suspend fun getAll(): List<Doctor>
    {
        //TODO("not implemented")
        return List(50) { index ->
            Doctor(UUID.randomUUID(), "Dr. $index", "special $index", dummyAddress)
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