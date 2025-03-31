package com.example.drivenext.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val middlename: String?,
    val email: String,
    val password: String,
    val birthDate: String,
    val gender: String,
    val licenceNumber: String,
    val licenceDate: String,
    var profilePic: String,
    val licencePic: String,
    val passportPic: String
)
