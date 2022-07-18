package com.example.semob

import com.example.semob.room.Car

class CarRepo {
    var carList = mutableListOf<Car>()

    fun getCars() = carList

    fun addCar(newList: List<Car>) {
        carList.clear()
        for (item in newList) {
            carList.add(item)
        }
    }
}