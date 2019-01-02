package com.example.home_pc.kotlinchatapp.mainactivity.view

import android.content.Context
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.RecyclerView
import com.example.home_pc.kotlinchatapp.R
import com.example.home_pc.kotlinchatapp.mainactivity.viewmodel.MainViewModel


class MessageAdapter(var viewModel: MainViewModel) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 100
        const val VIEW_TYPE_FIRST_USER_MESSAGE = 200
        const val VIEW_TYPE_SECOND_USER_MESSAGE = 300
        const val HEADER_POSITION = 0
    }

    lateinit var textView: TextView
    lateinit var editText: EditText
    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutResource: Int = viewModel.getLayout(viewType)
        val view: View = LayoutInflater.from(viewGroup.context).inflate(layoutResource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return viewModel.getViewType(position)
    }

    override fun getItemCount(): Int {
        return viewModel.messages.value!!.size + 1
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewModel.onBindRepositoryRowViewAtPosition(position, viewHolder)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener,
        MessageRowView {

        val txtCounter1 = itemView.findViewById<TextView>(R.id.txt_counter1)
        val txtCounter2 = itemView.findViewById<TextView>(R.id.txt_counter2)
        val txtText = itemView.findViewById<TextView>(R.id.txt)

        override fun bindHeader(firstMessageCounter: Int, secondMessageCounter: Int) {
            txtCounter1.text = firstMessageCounter.toString()
            txtCounter2.text = secondMessageCounter.toString()
        }

        override fun bind(text: String) {
            txtText.text = text
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, 1, 0, "Delete")
            menu.add(0, 2, 0, "Edit")
            menu.add(0, 3, 0, "Close")
            viewModel.selectedPosition = adapterPosition
            textView = itemView.findViewById(R.id.txt)
            editText = itemView.findViewById(R.id.ed_text)
            context = itemView.context
        }
    }

    fun onEditClick(position: Int) {
        val text = textView.text.toString()
        textView.visibility = View.GONE
        editText.visibility = View.VISIBLE
        editText.setText(text)
        editText.requestFocus()
        showKeyboard()

        editText.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    var newText: String = editText.text.toString()

                    if (newText.isEmpty()) {
                        newText = textView.text.toString()
                    }

                    editText.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.text = newText
                    viewModel.updateMessage(position, newText)
                }
                return true
            }
        })
    }

    private fun showKeyboard() {
        editText.postDelayed({
            val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(editText, 0)
        }, 200)
    }
}