package com.example.drivenext.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDao {

    @Query("SELECT * FROM cars")
    fun getAllCars(): LiveData<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<Car>)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("DELETE FROM cars")
    suspend fun deleteAll()
}
