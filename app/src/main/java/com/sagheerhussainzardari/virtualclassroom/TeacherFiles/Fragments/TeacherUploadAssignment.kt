package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.openLinkInBrowser
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_Assignments
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_teacher_uploadassignment.*

class TeacherUploadAssignment : Fragment() {

    var downloadUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_uploadassignment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDownloadUrlIfAvaiable()

        btn_upload.setOnClickListener {
            (activity as TeacherHomeActivity).uploadAssignment()
        }


        btn_viewAssignment.setOnClickListener {

            if (downloadUrl.isNotEmpty()) {
                context?.showToastShort("Opening Pdf In Browser!!!")
                openLinkInBrowser(downloadUrl, requireContext())
            } else {
                context?.showToastLong("Your Haven't Uploaded Any Assignments For ${TeacherHomeFragment.currentClassSelected!!.subjectName} Yet")
            }
        }

        tv_chooseAssignment.setOnClickListener {
            (activity as TeacherHomeActivity).selectPdfFromFiles()
        }
    }

    private fun getDownloadUrlIfAvaiable() {
        tv_assignmentName.text = TeacherHomeFragment.currentClassSelected!!.subjectName
        val baseRefForStoringClassInformation = DBRef_Assignments
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        downloadUrl = snapshot.child("downloadUrl").value.toString()
                    } else {
                        tv_assignmentName.text = ""
                        context?.showToastLong("Your Haven't Uploaded Any Assignments For ${TeacherHomeFragment.currentClassSelected!!.subjectName} Yet")
                    }
                }
            })
    }


}