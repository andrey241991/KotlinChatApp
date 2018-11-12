package com.example.home_pc.kotlinchatapp.mainactivity.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.example.home_pc.kotlinchatapp.OnMessageEditInterface
import com.example.home_pc.kotlinchatapp.R
import com.example.home_pc.kotlinchatapp.RecyclerItemDecorator
import com.example.home_pc.kotlinchatapp.SetPositionInterface
import com.example.home_pc.kotlinchatapp.entity.GeneralMessage
import com.example.home_pc.kotlinchatapp.entity.UserFirstMessage
import com.example.home_pc.kotlinchatapp.entity.UserSecondMessage
import com.example.home_pc.kotlinchatapp.mainactivity.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView, SetPositionInterface, OnMessageEditInterface {

    private var firstUserSelected: Boolean = true
    lateinit var adapter: MessageAdapter
    private lateinit var presenter: MainPresenter
    private var messages: ArrayList<GeneralMessage> = ArrayList()
    private var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        initAdapter()

        btnOk.setOnClickListener({ presenter.btnOkClick(edText.text.toString(), firstUserSelected, messages.size+1) })
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbFirst -> firstUserSelected = true
                R.id.rbSecond -> firstUserSelected = false
            }
        }
    }

    private fun initAdapter() {
        adapter = MessageAdapter()
        adapter.setPositionInterface = this
        adapter.onMessageEditInterface = this
        adapter.messages = messages
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.adapter = adapter
        recycler.addItemDecoration(RecyclerItemDecorator())
    }

    override fun sendMessageFromFirstUser(text: String, positionToAdd:Int) {
        messages.add(UserFirstMessage(text))
        presenter.setMessageCounters(messages)
        adapter.notifyItemInserted(positionToAdd)
    }

    override fun sendMessageFromSecondUser(text: String, positionToAdd:Int) {
        messages.add(UserSecondMessage(text))
        presenter.setMessageCounters(messages)
        adapter.notifyItemInserted(positionToAdd)
    }

    override fun setCounters(counters: Pair<Int, Int>) {
        adapter.firstMessageCounter = counters.first
        adapter.secondMessageCounter = counters.second
        adapter.notifyItemChanged(0)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            1 -> deleteMessage()
            2 -> adapter.onEditClick()
            3 -> cancelAction()
        }
        return true
    }

    override fun setPosition(position: Int) {
        selectedPosition = position
    }

    fun deleteMessage() {
        messages.removeAt(selectedPosition-1)
        presenter.setMessageCounters(messages)
        adapter.notifyItemRemoved(selectedPosition)
    }

    override fun editMessage(newText: String, selectedPosition: Int, firstUser: Boolean) {
        presenter.editMessage(newText, selectedPosition, firstUser)
    }

    override fun editMessageFromFirstUser(newText: String, selectedPosition: Int) {
        messages.removeAt(selectedPosition-1)
        messages.add(selectedPosition-1, UserFirstMessage(newText))
        adapter.notifyItemChanged(selectedPosition)
    }

    override fun editMessageFromSecondUser(newText: String, selectedPosition: Int) {
        messages.removeAt(selectedPosition-1)
        messages.add(selectedPosition-1, UserSecondMessage(newText))
        adapter.notifyItemChanged(selectedPosition)
    }

    fun cancelAction() {
        this.closeContextMenu()
    }

    override fun showEmptyErrorToast() {
        Toast.makeText(this, getString(R.string.empty_message), Toast.LENGTH_SHORT).show()
    }

}
