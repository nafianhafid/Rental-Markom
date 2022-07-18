package com.example.semob.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.semob.CarRepo

@Database(
    entities = [Car::class],
    version = 1
)
abstract class CarDB : RoomDatabase(){

    abstract fun carDao() : CarDao

    companion object {

        @Volatile private var instance : CarDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CarDB::class.java,
            "car.db"
        ).build()
    }
}