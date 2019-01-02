package com.example.home_pc.kotlinchatapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.home_pc.kotlinchatapp.entity.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user:User)

    @Query("SELECT * FROM users WHERE name = :userName")
    fun getUserByName(userName:String):User

}