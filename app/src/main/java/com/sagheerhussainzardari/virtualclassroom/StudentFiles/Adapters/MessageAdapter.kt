package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.easyandroid.openLinkInBrowser
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.CurrentClassModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.StudToStudChatModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import kotlinx.android.synthetic.main.layout_recycler_currentclasses.view.*
import kotlinx.android.synthetic.main.recyclerview_messages_layout.view.*

class MessageAdapter(
    var context: Context,
    var listOfMessages: ArrayList<StudToStudChatModel>
) : RecyclerView.Adapter<MessageAdapter.MyViewModel>() {

    class MyViewModel(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        return MyViewModel(
            LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_messages_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listOfMessages.size
    }

    override fun onBindViewHolder(holder: MessageAdapter.MyViewModel, position: Int) {

        if(listOfMessages[position].rollnumber == StudentHomeActivity.studentRollNumber){
            holder.view.tv_message1.text = listOfMessages[position].text;
            holder.view.tv_message2.visibility = View.INVISIBLE;
//            holder.view.roll_number.visibility = View.GONE
            holder.view.other.visibility = View.GONE
        }else{
            holder.view.tv_message2.text = listOfMessages[position].text;
            holder.view.tv_message1.visibility = View.INVISIBLE;
//            holder.view.roll_number.text = listOfMessages[position].rollnumber;
            holder.view.me.visibility = View.GONE
        }
    }
}