package com.example.home_pc.kotlinchatapp.mainactivity.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_pc.kotlinchatapp.R
import com.example.home_pc.kotlinchatapp.utils.RecyclerItemDecorator
import com.example.home_pc.kotlinchatapp.entity.Message
import com.example.home_pc.kotlinchatapp.mainactivity.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var firstUserSelected: Boolean = true
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        initAdapter()

        btnOk.setOnClickListener({ viewModel.btnOkClicked(edText.text.toString(), firstUserSelected) })
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbFirst -> firstUserSelected = true
                R.id.rbSecond -> firstUserSelected = false
            }
        }

        val messagesObserver = Observer<List<Message>> { newStrings ->
            viewModel.adapter.notifyDataSetChanged()
        }
        viewModel.messages.observe(this, messagesObserver)
    }

    private fun initAdapter() {
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler.adapter = viewModel.adapter
        recycler.addItemDecoration(RecyclerItemDecorator())
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        viewModel.onContextItemSelected(item)
        return true
    }
}