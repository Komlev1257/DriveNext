package com.example.drivenext.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.drivenext.data.AppDatabase
import com.example.drivenext.data.Car
import com.example.drivenext.repository.CarRepository
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CarRepository
    val allCars: LiveData<List<Car>>

    init {
        val carDao = AppDatabase.getDatabase(application).carDao()
        repository = CarRepository(carDao)
        allCars = repository.allCars
    }

    fun insertCar(car: Car) = viewModelScope.launch {
        repository.insertCar(car)
    }

    fun insertAll(cars: List<Car>) = viewModelScope.launch {
        repository.insertAll(cars)
    }

    fun deleteCar(car: Car) = viewModelScope.launch {
        repository.deleteCar(car)
    }

    fun deleteAllCars() = viewModelScope.launch {
        repository.deleteAllCars()
    }

    fun getCarById(id: Int): LiveData<Car?> {
        val result = MutableLiveData<Car?>()
        viewModelScope.launch {
            result.postValue(repository.getCarById(id))
        }
        return result
    }

}
