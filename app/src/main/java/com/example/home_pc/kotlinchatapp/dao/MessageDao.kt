package com.example.home_pc.kotlinchatapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.home_pc.kotlinchatapp.entity.Message

@Dao
interface MessageDao {

    @Insert
    fun insertMessage(message: Message)

    @Delete
    fun deleteMessage(message: Message)

    @Query("UPDATE messages SET text = :text WHERE userId = :messageId")
    fun updateMessage(text: String, messageId: Int)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): LiveData<MutableList<Message>>

}