package com.example.home_pc.kotlinchatapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    var text: String, var userId: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
