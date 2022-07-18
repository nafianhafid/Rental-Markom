package com.example.semob.room

import androidx.room.*

@Dao
interface CarDao {

    @Insert
    suspend fun addCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("SELECT * FROM car WHERE status = 0 ORDER BY id DESC")
    suspend fun getCars(): List<Car>

    @Query("SELECT * FROM car WHERE status = 1 ORDER BY id DESC")
    suspend fun getCustomers(): List<Car>

    @Query("SELECT * FROM car WHERE id=:idInput")
    suspend fun getCar(idInput: Int): List<Car>
}