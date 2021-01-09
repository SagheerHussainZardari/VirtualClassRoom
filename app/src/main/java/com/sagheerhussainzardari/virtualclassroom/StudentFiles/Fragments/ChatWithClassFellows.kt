package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.virtualclassroom.DBRef_ChatStdToStd
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Adapters.MessageAdapter
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.StudToStudChatModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentBatch
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentDegree
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentDept
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentFaculity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentGroup
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity.Companion.studentTime
import kotlinx.android.synthetic.main.fragment_student_chat_with_class_fellows.*
import java.util.*

class ChatWithClassFellows : Fragment() {

    companion object {
        var listOfMessages = ArrayList<StudToStudChatModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_chat_with_class_fellows, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        getAllMessages()

        try {
            btn_send_stdTostd.setOnClickListener {


                var studentFaculity = StudentHomeActivity.studentFaculity
                var studentDept = StudentHomeActivity.studentDept
                var studentDegree = StudentHomeActivity.studentDegree
                var studentTime = StudentHomeActivity.studentTime
                var studentBatch = StudentHomeActivity.studentBatch
                var studentGroup = StudentHomeActivity.studentGroup
                var studentRollNumber = StudentHomeActivity.studentRollNumber

                var stdChatRef = DBRef_ChatStdToStd
                    .child(studentFaculity)
                    .child(studentDept)
                    .child(studentDegree)
                    .child(studentTime)
                    .child(studentBatch)
                    .child(studentGroup).push().key.toString()

                var messageStoreRef = DBRef_ChatStdToStd
                    .child(studentFaculity)
                    .child(studentDept)
                    .child(studentDegree)
                    .child(studentTime)
                    .child(studentBatch)
                    .child(studentGroup)
                    .child(stdChatRef)

                if(et_message.text.toString().isNotEmpty()) {

                    messageStoreRef.child("text").setValue(et_message.text.toString())
                    messageStoreRef.child("datetime").setValue(System.currentTimeMillis())
                    messageStoreRef.child("rollnumber").setValue(studentRollNumber)

                    et_message.setText("")

                }else{
                    et_message.setText("")
                    Toast.makeText(
                        requireContext(),
                        "Message Field Can Not Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }catch (e: Exception){

        }


    }

    fun getAllMessages(){

        try {
            var messageStoreRef = DBRef_ChatStdToStd
                .child(studentFaculity)
                .child(studentDept)
                .child(studentDegree)
                .child(studentTime)
                .child(studentBatch)
                .child(studentGroup).addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        listOfMessages.clear()


                        for (document in snapshot.children){
                            listOfMessages.add(StudToStudChatModel(document.child("datetime").value.toString() , document.child("text").value.toString() , document.child("rollnumber").value.toString()))
                        }
                        listOfMessages.sortedBy { it -> it.datetime }

                        try {
                            messageRecyclerView?.setHasFixedSize(true)
                            messageRecyclerView?.layoutManager = LinearLayoutManager(context)
                            messageRecyclerView?.adapter = MessageAdapter(requireContext(), listOfMessages)

                            messageRecyclerView?.smoothScrollToPosition(listOfMessages.size)
                        }catch (e: Exception){

                        }

                    }
                })

        }catch (e: Exception){

        }


    }

}