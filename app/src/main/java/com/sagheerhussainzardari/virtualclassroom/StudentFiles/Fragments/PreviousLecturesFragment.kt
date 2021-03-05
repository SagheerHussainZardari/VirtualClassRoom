package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.virtualclassroom.DBRef_LECTURES
import com.sagheerhussainzardari.virtualclassroom.DBRef_Subjects
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.YoutubeLecturesModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters.YoutubeLecturesAdapter
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters.YoutubePreviousLecturesAdapter
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.YoutubeLecturesFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.YoutubeLectures
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_previous_lectures.*
import kotlinx.android.synthetic.main.fragment_student_assignment.*
import kotlinx.android.synthetic.main.fragment_youtube_lectures.*

class PreviousLecturesFragment : Fragment() {

    var subjects = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_lectures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSubjectSpinner()

        spinner_selectsubject_studentLectures.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    getLecturesList(subjects[p2].substringBefore(' '))
                }
            }

    }



    private fun setSubjectSpinner() {

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

                    spinner_selectsubject_studentLectures?.adapter =
                        ArrayAdapter<String>(requireContext(), R.layout.spinner_layout, subjects)

                }
            })

    }


    private fun getLecturesList(subjectCode: String) {

        DBRef_LECTURES
            .child(StudentHomeActivity.studentFaculity)
            .child(StudentHomeActivity.studentDept)
            .child(StudentHomeActivity.studentDegree.toUpperCase())
            .child(StudentHomeActivity.studentTime)
            .child(StudentHomeActivity.studentBatch.toUpperCase())
            .child(StudentHomeActivity.studentGroup.toUpperCase())
            .child(subjectCode)
            .addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var listOfLectures = ArrayList<YoutubeLecturesModel>()

                for(document in snapshot.children){
                    listOfLectures.add(
                        YoutubeLecturesModel(
                            document.child("title").value.toString(),
                            document.child("link").value.toString(),
                            document.key.toString()
                        )
                    )
                }

                rv_previous_lectures?.setHasFixedSize(true)
                rv_previous_lectures?.layoutManager = LinearLayoutManager(context)
                rv_previous_lectures?.adapter = YoutubePreviousLecturesAdapter(requireContext(), listOfLectures , PreviousLecturesFragment())

            }
        })


    }


}