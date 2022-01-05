package com.example.haritagedemo.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun registerUser(users: Users)

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun readAllUsers():LiveData<List<Users>>

    @Query("SELECT * FROM users_table WHERE userId LIKE :userId AND password LIKE :password")
    fun getUserId(userId: String, password: String): Users?
}