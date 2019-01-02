package com.example.home_pc.kotlinchatapp.repository

import androidx.annotation.WorkerThread
import com.example.home_pc.kotlinchatapp.app.App
import com.example.home_pc.kotlinchatapp.dao.UserDao
import com.example.home_pc.kotlinchatapp.entity.User

class UserRepository(app:App) {
    val userDao: UserDao
    init {
        userDao = app.getDataBase().userDao()
    }

    @WorkerThread
    suspend fun insertUser(user:User){
        userDao.insertUser(user)
    }

    @WorkerThread
    suspend fun getUserByName(userName:String): User{
        return userDao.getUserByName(userName)
    }

}