package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.openLinkInBrowser
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_Results
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_teacher_upload_results.*
import kotlinx.android.synthetic.main.fragment_teacher_uploadassignment.btn_upload

class TeacherUploadResults : Fragment() {

    var downloadUrl = "";
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_upload_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDownloadUrlIfAvaiable(DBRef_Results)

        btn_upload.setOnClickListener {
            (activity as TeacherHomeActivity).uploadResults()
        }


        btn_viewResult.setOnClickListener {

            if (downloadUrl.isNotEmpty()) {
                context?.showToastShort("Opening Pdf In Browser!!!")
                openLinkInBrowser(downloadUrl, requireContext())
            } else {
                context?.showToastLong("Your Haven't Uploaded Any Results For ${TeacherHomeFragment.currentClassSelected!!.subjectName} Yet")
            }
        }

        tv_chooseResult.setOnClickListener {
            (activity as TeacherHomeActivity).selectPdfFromFiles()
        }
    }

    private fun getDownloadUrlIfAvaiable(ref: DatabaseReference) {
        tv_resultName.text = TeacherHomeFragment.currentClassSelected!!.subjectName
        val baseRefForStoringClassInformation = ref
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        downloadUrl = snapshot.child("downloadUrl").value.toString()
                    } else {
                        //tv_assignmentName.text = ""
                        context?.showToastLong("Your Haven't Uploaded Any Results For ${TeacherHomeFragment.currentClassSelected!!.subjectName} Yet")
                    }
                }
            })
    }
}