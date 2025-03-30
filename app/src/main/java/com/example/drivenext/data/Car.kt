package com.example.drivenext.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    val model: String,
    val brand: String,
    val pricePerDay: Int,
    val transmission: String,
    val fuelType: String,
    val imageResId: Int
)