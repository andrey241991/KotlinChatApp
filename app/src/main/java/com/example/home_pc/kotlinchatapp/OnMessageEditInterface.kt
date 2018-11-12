package com.example.home_pc.kotlinchatapp

interface OnMessageEditInterface {
    fun editMessage(newText: String, selectedPosition: Int, firstUser: Boolean)
}