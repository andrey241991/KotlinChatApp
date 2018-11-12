package com.example.home_pc.kotlinchatapp.mainactivity.presenter

import com.example.home_pc.kotlinchatapp.MessageCounterHelper
import com.example.home_pc.kotlinchatapp.entity.GeneralMessage
import com.example.home_pc.kotlinchatapp.mainactivity.view.MainActivityView

class MainPresenter(private val mainActivityView: MainActivityView) {

    fun btnOkClick(messageText: String, firstUserSelected: Boolean, positionToAdd:Int) {

        if (messageText.isEmpty()) {
            mainActivityView.showEmptyErrorToast()
            return
        }

        when (firstUserSelected) {
            true -> mainActivityView.sendMessageFromFirstUser(messageText, positionToAdd)
            false -> mainActivityView.sendMessageFromSecondUser(messageText, positionToAdd)
        }
    }

    fun setMessageCounters(messages: ArrayList<GeneralMessage>) {
        val counters: Pair<Int, Int> = MessageCounterHelper.countMessage(messages)
        mainActivityView.setCounters(counters);
    }

    fun editMessage(newText: String, selectedPosition: Int, firstUser: Boolean) {
        when (firstUser) {
            true -> mainActivityView.editMessageFromFirstUser(newText, selectedPosition)
            false -> mainActivityView.editMessageFromSecondUser(newText, selectedPosition)
        }
    }

}