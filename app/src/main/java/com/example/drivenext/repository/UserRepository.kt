package com.example.drivenext.repository

import androidx.lifecycle.LiveData
import com.example.drivenext.data.User
import com.example.drivenext.data.UserDao

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    suspend fun update(user: User) {
        userDao.updateUser(user)
    }

    suspend fun delete(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    suspend fun authenticate(email: String, password: String): User? {
        return userDao.authenticate(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
