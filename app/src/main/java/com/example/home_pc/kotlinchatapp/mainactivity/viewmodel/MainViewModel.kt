package com.example.home_pc.kotlinchatapp.mainactivity.viewmodel

import android.app.Application
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.home_pc.kotlinchatapp.utils.MessageCounterHelper
import com.example.home_pc.kotlinchatapp.R
import com.example.home_pc.kotlinchatapp.app.App
import com.example.home_pc.kotlinchatapp.entity.*
import com.example.home_pc.kotlinchatapp.mainactivity.view.MessageAdapter
import com.example.home_pc.kotlinchatapp.mainactivity.view.MessageAdapter.Companion.HEADER_POSITION
import com.example.home_pc.kotlinchatapp.mainactivity.view.MessageAdapter.Companion.VIEW_TYPE_FIRST_USER_MESSAGE
import com.example.home_pc.kotlinchatapp.mainactivity.view.MessageAdapter.Companion.VIEW_TYPE_HEADER
import com.example.home_pc.kotlinchatapp.mainactivity.view.MessageAdapter.Companion.VIEW_TYPE_SECOND_USER_MESSAGE
import com.example.home_pc.kotlinchatapp.repository.MessageRepository
import com.example.home_pc.kotlinchatapp.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var messageCounterHelper: MessageCounterHelper

    val userRepository = UserRepository(application as App)
    val messageRepository = MessageRepository(application as App)
    val adapter = MessageAdapter(this)
    var messages: LiveData<MutableList<Message>> = getAllMessages()
    var selectedPosition: Int = 0
    var user1: User? = null
    var user2: User? = null

    init {
        App.getDaggerAppComponent().inject(this)
        initUser()
        if(user1 == null || user2 == null){
            addNewUser("user1")
            addNewUser("user2")
            initUser()
        }
    }

    fun initUser(){
        user1 = getUserByName("user1")
        user2 = getUserByName("user2")
    }

    fun btnOkClicked(messageText: String, firstUserSelected: Boolean) {
        when (firstUserSelected) {
            true -> addNewMessage(user1!!.id, messageText)
            false -> addNewMessage(user2!!.id, messageText)
        }
    }

    fun addNewUser(userName: String) {
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                userRepository.insertUser(User(userName))
            }.join()
        }
    }

    fun getUserByName(userName: String): User? {
        var user: User? = null
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                user = userRepository.getUserByName(userName)
            }.join()
        }
        return user
    }

    fun addNewMessage(userId: Int, messageText: String) {
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                messageRepository.insertMessage(Message(messageText, userId))
            }.join()
        }
    }

    fun deleteMessage(message: Message) {
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                messageRepository.deleteMessage(message)
            }.join()
        }
    }

    fun updateMessage(position: Int, newText: String) {
        var id: Int = messages.value!!.get(position - 1).id
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                messageRepository.updateMessage(newText, id)
            }.join()
        }
    }

    fun getAllMessages(): LiveData<MutableList<Message>> {
        return messageRepository.getAllMessages()
    }

    fun getLayout(viewType: Int): Int {
        when (viewType) {
            VIEW_TYPE_HEADER -> return R.layout.header_item
            VIEW_TYPE_FIRST_USER_MESSAGE -> return R.layout.first_user_item
            VIEW_TYPE_SECOND_USER_MESSAGE -> return R.layout.second_user_item
        }
        return R.layout.first_user_item
    }

    fun getViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_HEADER
        }

        val currentMessage: Message = messages.value!!.get(position - 1)
        when {
            currentMessage.userId.equals(user1!!.id) -> return VIEW_TYPE_FIRST_USER_MESSAGE
            currentMessage.userId.equals(user2!!.id) -> return VIEW_TYPE_SECOND_USER_MESSAGE
        }
        return VIEW_TYPE_FIRST_USER_MESSAGE
    }

    fun onBindRepositoryRowViewAtPosition(position: Int, holder: MessageAdapter.ViewHolder) {
        if (position == HEADER_POSITION) {
            var count = messageCounterHelper.countMessage(messages, user1!!.id, user2!!.id)
            holder.bindHeader(count.first, count.second)
        } else {
            val text = messages.value!!.get(position - 1).text
            holder.bind(text)
        }
    }

    fun onContextItemSelected(item: MenuItem) {
        when (item.itemId) {
            1 -> deleteMessage(messages.value!!.get(selectedPosition - 1))
            2 -> adapter.onEditClick(selectedPosition)
        }
    }
}
