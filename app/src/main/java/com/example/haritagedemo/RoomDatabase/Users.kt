package com.example.haritagedemo.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @ColumnInfo(name = "userId")
    val userId:String,

    @ColumnInfo(name = "password")
    val password:String,

)