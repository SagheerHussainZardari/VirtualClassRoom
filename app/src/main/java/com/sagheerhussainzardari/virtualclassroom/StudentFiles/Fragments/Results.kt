package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.openLinkInBrowser
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_Results
import com.sagheerhussainzardari.virtualclassroom.DBRef_Subjects
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import kotlinx.android.synthetic.main.fragment_student_results.*

class Results : Fragment() {
    var downloadUrl = "";

    var subjects = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setSubjectSpinner()

        spinner_selectsubject_studentResult.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    getDownloadUrlIfAvaiable(
                        subjects[p2].substringBefore(' '),
                        subjects[p2].substringAfter(' ')
                    )
                }
            }


        btn_viewResult.setOnClickListener {
            if (downloadUrl.isNotEmpty()) {
                context?.showToastShort("Opening Pdf In Browser!!!")
                openLinkInBrowser(downloadUrl, requireContext())
            } else {
                context?.showToastLong("Your Teacher Haven't Uploaded Any Results For This Subject Yet")
            }
        }
    }


    private fun setSubjectSpinner() {
        pb_studentResult?.visibility = View.VISIBLE

        DBRef_Subjects
            .child(StudentHomeActivity.studentFaculity)
            .child(StudentHomeActivity.studentDept)
            .child(StudentHomeActivity.studentDegree.toUpperCase())
            .child(StudentHomeActivity.studentTime)
            .child(StudentHomeActivity.studentBatch.toUpperCase())
            .child(StudentHomeActivity.studentGroup.toUpperCase())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (subject in snapshot.children) {
                        subjects.add(
                            subject.key.toString() + " " + subject.child("subjectName").value.toString()
                        )
                    }
                    spinner_selectsubject_studentResult?.adapter =
                        ArrayAdapter<String>(requireContext(), R.layout.spinner_layout, subjects)


                }
            })

    }

    private fun getDownloadUrlIfAvaiable(subject: String, subjectName: String) {

        val baseRefForStoringClassInformation = DBRef_Results
            .child(StudentHomeActivity.studentFaculity)
            .child(StudentHomeActivity.studentDept)
            .child(StudentHomeActivity.studentDegree.toUpperCase())
            .child(StudentHomeActivity.studentTime)
            .child(StudentHomeActivity.studentBatch.toUpperCase())
            .child(StudentHomeActivity.studentGroup.toUpperCase())
            .child(subject) // replace with the subject selected by the studetn from spinner
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        downloadUrl = snapshot.child("downloadUrl").value.toString()
                        tv_resultName?.text = "$subjectName's Result";
                        pb_studentResult?.visibility = View.GONE

                    } else {
                        tv_resultName?.text = "No Result"
                        downloadUrl = "";
                        pb_studentResult?.visibility = View.GONE
                        context?.showToastLong("Your Teacher Haven't Uploaded Any Assignments For $subjectName Yet")
                    }
                }
            })
    }

}