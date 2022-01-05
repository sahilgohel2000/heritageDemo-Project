package com.example.haritagedemo.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {

    abstract fun UserDao(): UserDao

    companion object{

        @Volatile
        private var INSTANCE: UserDatabase?=null

        fun getDatabase(context: Context): UserDatabase {
            val instance= INSTANCE
            if (instance!=null)
            {
                return instance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext, UserDatabase::class.java,"user_database"
                ).allowMainThreadQueries()
                    .build()

                INSTANCE =instance
                return instance
            }
        }
    }
}