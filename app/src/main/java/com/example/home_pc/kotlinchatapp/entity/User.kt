package com.example.home_pc.kotlinchatapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val name: String, @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)