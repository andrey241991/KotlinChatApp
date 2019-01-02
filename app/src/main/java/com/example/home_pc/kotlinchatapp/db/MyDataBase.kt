package com.example.home_pc.kotlinchatapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.home_pc.kotlinchatapp.dao.MessageDao
import com.example.home_pc.kotlinchatapp.dao.UserDao
import com.example.home_pc.kotlinchatapp.entity.Message
import com.example.home_pc.kotlinchatapp.entity.User

@Database(entities = arrayOf(User::class, Message::class), version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        const val NAME = "MyDataBase"
    }

}