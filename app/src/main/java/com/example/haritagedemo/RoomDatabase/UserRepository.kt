package com.example.haritagedemo.RoomDatabase

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(users: Users){
        userDao.registerUser(users)
    }

    val readAllData: LiveData<List<Users>> = userDao.readAllUsers()

    suspend fun getUserId(userId: String, password: String): Users?{
        return userDao.getUserId(userId,password)
    }
}