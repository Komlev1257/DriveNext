package com.example.drivenext.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.drivenext.data.AppDatabase
import com.example.drivenext.data.User
import com.example.drivenext.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }

    fun getUserById(id: Int): LiveData<User?> {
        val userLiveData = MutableLiveData<User?>()
        viewModelScope.launch {
            userLiveData.postValue(repository.getUserById(id))
        }
        return userLiveData
    }

    fun authenticate(email: String, password: String): LiveData<User?> {
        val authResult = MutableLiveData<User?>()
        viewModelScope.launch {
            authResult.postValue(repository.authenticate(email, password))
        }
        return authResult
    }

    fun insertAndThen(user: User, onComplete: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insert(user)
                onComplete()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun getUserByEmail(email: String): LiveData<User?> {
        val result = MutableLiveData<User?>()
        viewModelScope.launch {
            result.postValue(repository.getUserByEmail(email))
        }
        return result
    }
}
