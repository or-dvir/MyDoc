package com.hotmail.or_dvir.mydoc.repositories

interface BaseRepository<T>
{
    suspend fun getAll(): List<T>

    suspend fun search(searchQuery: String): List<T>
    //todo
//    suspend fun getById(id: UUID): T?

    /**
     * @return Boolean `true` if successful, `false` otherwise
     */
    suspend fun add(obj: T): Boolean

    /**
     * @return Boolean `true` if successful, `false` otherwise
     */
    suspend fun delete(obj: T): Boolean
}