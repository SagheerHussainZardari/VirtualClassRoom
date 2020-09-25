package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.virtualclassroom.DBRef_Notes
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_teacher_write_on_notice_board.*

class WriteOnNoticeBoard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_write_on_notice_board, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentNoteValue()

        btn_writeOnNoticeBoard.setOnClickListener {
            var note = et_writeOnNoticeBoard.text.toString();

            var storageArea = DBRef_Notes.child(TeacherHomeActivity.teacherFaculty)
                .child(TeacherHomeActivity.teacherDept)
                .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
                .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
                .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
                .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
                .child(TeacherHomeFragment.currentClassSelected!!.subjectCode).child("note")
                .setValue(note);

            tv_currentNote.text = note;
            Toast.makeText(context, "Note Uploaded Successfully!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setCurrentNoteValue() {
        DBRef_Notes.child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode).child("note")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value.toString() != "null") {
                        tv_currentNote.text = snapshot.value.toString();
                    } else {
                        Toast.makeText(context, "Not Note Uploaded Yet!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }


}