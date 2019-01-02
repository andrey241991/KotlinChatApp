package com.example.home_pc.kotlinchatapp.mainactivity.view

interface MessageRowView {
    fun bind(text: String)
    fun bindHeader(firstMessageCounter: Int, secondMessageCounter: Int)
}