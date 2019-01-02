package com.example.home_pc.kotlinchatapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.home_pc.kotlinchatapp.app.App
import com.example.home_pc.kotlinchatapp.dao.MessageDao
import com.example.home_pc.kotlinchatapp.entity.Message

class MessageRepository(app: App) {
    val messageDao:MessageDao
    init {
        messageDao = app.getDataBase().messageDao()
    }

    @WorkerThread
    suspend fun insertMessage(message: Message){
        messageDao.insertMessage(message)
    }

    @WorkerThread
    suspend fun deleteMessage(message: Message){
        messageDao.deleteMessage(message)
    }

    @WorkerThread
    suspend fun updateMessage(newText: String, messageId:Int){
        messageDao.updateMessage(newText, messageId)
    }

    fun getAllMessages(): LiveData<MutableList<Message>> {
        return messageDao.getAllMessages()
    }

}