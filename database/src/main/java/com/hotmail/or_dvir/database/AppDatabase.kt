package com.hotmail.or_dvir.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hotmail.or_dvir.database.daos.DoctorsDao
import com.hotmail.or_dvir.database.entities.DoctorEntity

@Database(
    entities = arrayOf(DoctorEntity::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun userDao(): DoctorsDao

    companion object
    {
        //todo when app is done, ifthe name of the app changed, also change this
        const val DB_NAME = "MyDoc.db"

        //Singleton prevents multiple instances of database opening at the
        //same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase
        {
            //if the INSTANCE is not null, then return it,
            //if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}