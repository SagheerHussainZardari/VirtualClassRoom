package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

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
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import kotlinx.android.synthetic.main.fragment_student_assignment.*

class StudentAssignment : Fragment() {
    var downloadUrl = "";

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_assignment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDownloadUrlIfAvaiable()


        btn_viewAssignment_student.setOnClickListener {
            if (downloadUrl.isNotEmpty()) {
                context?.showToastShort("Opening Pdf In Browser!!!")
                openLinkInBrowser(downloadUrl, requireContext())
            } else {
                context?.showToastLong("Your Haven't Uploaded Any Assignments For ${TeacherHomeFragment.currentClassSelected!!.subjectName} Yet")
            }
        }
    }

    private fun getDownloadUrlIfAvaiable() {
        pb_studentAssignment.visibility = View.VISIBLE
        val baseRefForStoringClassInformation = DBRef_Assignments
            .child(StudentHomeActivity.studentFaculity)
            .child(StudentHomeActivity.studentDept)
            .child(StudentHomeActivity.studentDegree.toUpperCase())
            .child(StudentHomeActivity.studentTime)
            .child(StudentHomeActivity.studentBatch.toUpperCase())
            .child(StudentHomeActivity.studentGroup.toUpperCase())
            .child("Electronics") // replace with the subject selected by the studetn from spinner
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        downloadUrl = snapshot.child("downloadUrl").value.toString()
                        tv_assignmentName_student.text = "Yes There Is Assignment";
                        pb_studentAssignment.visibility = View.GONE

                    } else {
                        tv_assignmentName_student.text = "No Assignemnt"
                        pb_studentAssignment.visibility = View.GONE
                        context?.showToastLong("Your Haven't Uploaded Any Assignments For Electronics Yet")
                    }
                }
            })
    }


}