package com.example.home_pc.kotlinchatapp.mainactivity.view

interface MainActivityView {
    fun sendMessageFromFirstUser(text:String, positionToAdd:Int)
    fun sendMessageFromSecondUser(text:String,  positionToAdd:Int)
    fun setCounters(counters: Pair<Int, Int>)
    fun showEmptyErrorToast()
    fun editMessageFromFirstUser(newText:String, selectedPosition:Int)
    fun editMessageFromSecondUser(newText:String,  selectedPosition:Int)
}