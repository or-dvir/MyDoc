package com.hotmail.or_dvir.mydoc.repositories

import com.hotmail.or_dvir.mydoc.models.Doctor

class DoctorsRepositoryImpl : DoctorsRepository
{
    override suspend fun getAll(): List<Doctor>
    {
        //TODO("not implemented")
        return listOf()
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
}