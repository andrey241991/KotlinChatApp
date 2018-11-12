package com.example.home_pc.kotlinchatapp

import com.example.home_pc.kotlinchatapp.entity.GeneralMessage
import com.example.home_pc.kotlinchatapp.entity.UserFirstMessage

class MessageCounterHelper {

    companion object {
        fun countMessage(messages: ArrayList<GeneralMessage>):Pair<Int, Int> {
            var first = 0
            var second = 0

            for (i in 0..messages.size-1) {
                when (messages.get(i)) {
                    is UserFirstMessage -> first++
                    else -> second++
                }
            }
            return first to second
        }
    }
}
