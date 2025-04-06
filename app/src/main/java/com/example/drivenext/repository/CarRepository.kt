package com.example.drivenext.repository

import androidx.lifecycle.LiveData
import com.example.drivenext.data.Car
import com.example.drivenext.data.CarDao

class CarRepository(private val carDao: CarDao) {

    val allCars: LiveData<List<Car>> = carDao.getAllCars()

    suspend fun insertCar(car: Car) {
        carDao.insertCar(car)
    }

    suspend fun insertAll(cars: List<Car>) {
        carDao.insertAll(cars)
    }

    suspend fun deleteCar(car: Car) {
        carDao.deleteCar(car)
    }

    suspend fun deleteAllCars() {
        carDao.deleteAll()
    }

    suspend fun getCarById(id: Int): Car? {
        return carDao.getCarById(id)
    }
}
