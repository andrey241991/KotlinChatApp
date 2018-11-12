package com.example.home_pc.kotlinchatapp.mainactivity.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.example.home_pc.kotlinchatapp.OnMessageEditInterface
import com.example.home_pc.kotlinchatapp.R
import com.example.home_pc.kotlinchatapp.SetPositionInterface
import com.example.home_pc.kotlinchatapp.entity.GeneralMessage
import com.example.home_pc.kotlinchatapp.entity.UserFirstMessage
import com.example.home_pc.kotlinchatapp.entity.UserSecondMessage


class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    val VIEW_TYPE_HEADER = 100
    val VIEW_TYPE_FIRST_USER_MESSAGE = 200
    val VIEW_TYPE_SECOND_USER_MESSAGE = 300
    val HEADER_POSITION = 0

    var messages: ArrayList<GeneralMessage> = ArrayList()
    var firstMessageCounter: Int = 0
    var secondMessageCounter: Int = 0
    var selectedPosition: Int = 0

    lateinit var onMessageEditInterface: OnMessageEditInterface
    lateinit var setPositionInterface: SetPositionInterface
    lateinit var textView: TextView
    lateinit var editText: EditText
    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutResource: Int = getLayout(viewType)
        val view: View = LayoutInflater.from(viewGroup.context).inflate(layoutResource, viewGroup, false)
        return ViewHolder(view)
    }

    private fun getLayout(viewType: Int): Int {
        when (viewType) {
            VIEW_TYPE_HEADER -> return R.layout.header_item
            VIEW_TYPE_FIRST_USER_MESSAGE -> return R.layout.first_user_item
            VIEW_TYPE_SECOND_USER_MESSAGE -> return R.layout.second_user_item
        }
        return R.layout.first_user_item
    }

    override fun getItemViewType(position: Int): Int {

        if (position == 0){
            return VIEW_TYPE_HEADER
        }

        val currentMessage: GeneralMessage = messages.get(position -1)
        when{
            currentMessage is UserFirstMessage -> return VIEW_TYPE_FIRST_USER_MESSAGE
            currentMessage is UserSecondMessage -> return VIEW_TYPE_SECOND_USER_MESSAGE
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if(position == HEADER_POSITION){
            viewHolder.bindHeader(firstMessageCounter, secondMessageCounter)
        }else{
            val text = messages.get(position -1).name
            viewHolder.bind(text)
        }
    }

    override fun getItemCount(): Int {
        return messages.size+1
    }

    fun onEditClick() {
        val text: String = textView.text.toString()
        textView.visibility = View.GONE
        editText.visibility = View.VISIBLE
        editText.setText(text)
        editText.requestFocus()
        showKeyboard()

        editText.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    var newText: String = editText.text.toString()

                    if (newText.isEmpty()) {
                        newText = textView.text.toString()
                    }

                    editText.visibility = View.GONE
                    textView.visibility = View.VISIBLE
                    textView.text = newText
                    val firstUser: Boolean = (messages.get(selectedPosition-1) is UserFirstMessage)
                    onMessageEditInterface.editMessage(newText, selectedPosition, firstUser)

                }
                return true
            }
        })
    }

   private fun showKeyboard(){
        editText.postDelayed({
            val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(editText, 0)
        }, 200)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu!!.setHeaderTitle("Select The Action");
            menu.add(0, 1, 0, "Delete");
            menu.add(0, 2, 0, "Edit");
            menu.add(0, 3, 0, "Close");
            selectedPosition = adapterPosition
            setPositionInterface.setPosition(selectedPosition)
            textView = itemView.findViewById(R.id.txt)
            editText = itemView.findViewById(R.id.ed_text)
            context = itemView.context
        }

        fun bind(text: String) {
            itemView.findViewById<TextView>(R.id.txt).text = text
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bindHeader(firstMessageCounter: Int, secondMessageCounter: Int) {
            itemView.findViewById<TextView>(R.id.txt_counter1).text = firstMessageCounter.toString()
            itemView.findViewById<TextView>(R.id.txt_counter2).text = secondMessageCounter.toString()
        }
    }
}