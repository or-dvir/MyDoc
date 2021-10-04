package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.Clinic

class ClinicsRepositoryImpl : ClinicsRepository
{
    override suspend fun getAll(): List<Clinic>
    {
        //TODO("not implemented")
        return listOf()
    }

    override suspend fun search(searchQuery: String): List<Clinic>
    {
        //TODO("not implemented")
        return listOf()
    }

    override suspend fun add(obj: Clinic): Boolean
    {
        //TODO("not implemented")
        return true
    }

    override suspend fun delete(obj: Clinic): Boolean
    {
        //TODO("not implemented")
        return true
    }
}