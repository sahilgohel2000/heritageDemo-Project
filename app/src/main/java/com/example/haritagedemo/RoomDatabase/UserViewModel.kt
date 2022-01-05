package com.example.haritagedemo.RoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.haritagedemo.RoomDatabase.UserDatabase
import com.example.haritagedemo.RoomDatabase.UserRepository
import com.example.haritagedemo.RoomDatabase.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Users>>
    private val repository: UserRepository

    init {
        val UserDao = UserDatabase.getDatabase(application).UserDao()
        repository = UserRepository(UserDao)
        readAllData = repository.readAllData
    }

    fun registerUser(users: Users){
        viewModelScope.launch(Dispatchers.IO){
            repository.registerUser(users)
        }
    }

    fun getUserId(userId: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserId(userId = String(), password = String())
        }
    }
}