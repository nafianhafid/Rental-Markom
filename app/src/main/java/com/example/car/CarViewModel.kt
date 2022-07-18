package com.example.semob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.semob.room.Car

class CarViewModel(val carRepo: CarRepo) : ViewModel() {
    private val cars = MutableLiveData<List<Car>>()

    fun getCars(): LiveData<List<Car>> {
        cars.value = carRepo.getCars()
        return cars
    }

    fun addCar(newCar: List<Car>) {
        carRepo.addCar(newCar)
    }
}