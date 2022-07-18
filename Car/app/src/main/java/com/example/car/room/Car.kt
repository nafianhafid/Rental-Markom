package com.example.semob.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var category: String,
    var price: Int,
    var status: Int,
    var customer: String,
    var phoneNumber: String,
    var day: Int
)