package com.example.home_pc.kotlinchatapp.utils

import androidx.lifecycle.LiveData
import com.example.home_pc.kotlinchatapp.entity.Message

class MessageCounterHelper {
    fun countMessage(messages: LiveData<MutableList<Message>>, userId1: Int, userId2: Int): Pair<Int, Int> {
        var first = 0
        var second = 0

        for (i in 0..messages.value!!.size - 1) {
            when (messages.value!!.get(i).userId) {
                userId1 -> first++
                userId2 -> second++
            }
        }
        return first to second
    }
}
