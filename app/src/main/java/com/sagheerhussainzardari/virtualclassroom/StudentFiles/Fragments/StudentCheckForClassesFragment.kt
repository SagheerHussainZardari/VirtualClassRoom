package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.hide
import com.sagheerhussainzardari.easyandroid.show
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.virtualclassroom.DBRef_ScheduledClasses
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Adapters.CurrentClassesAdapter
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.CurrentClassModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import kotlinx.android.synthetic.main.fragment_student_checkforclasses.*

class StudentCheckForClassesFragment : Fragment() {


    companion object {
        var currentClasses = ArrayList<CurrentClassModel>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_checkforclasses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb_checkForClasses?.show()
        DBRef_ScheduledClasses
            .child(StudentHomeActivity.studentFaculity)
            .child(StudentHomeActivity.studentDept)
            .child(StudentHomeActivity.studentDegree)
            .child(StudentHomeActivity.studentTime)
            .child(StudentHomeActivity.studentBatch)
            .child(StudentHomeActivity.studentGroup)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    pb_checkForClasses?.hide()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    onDataGetSuccess(snapshot)
                }
            })

    }

    private fun onDataGetSuccess(snapshot: DataSnapshot) {

        if (snapshot.hasChildren()) {
            currentClasses.clear()

            for (classs in snapshot.children) {
                currentClasses.add(
                    CurrentClassModel(
                        classs.child("classSubject").value.toString(),
                        classs.child("classTeacher").value.toString(),
                        classs.child("classTime").value.toString(),
                        classs.child("classDate").value.toString(),
                        classs.child("classZoomID").value.toString(),
                        classs.child("classZoomPassword").value.toString(),
                        classs.child("classZoomLink").value.toString()
                    )
                )
            }

            setUpRecyclerView()

        } else {
            pb_checkForClasses?.hide()
            context?.showToastLong("You Have No Classes Yet")
        }

    }

    private fun setUpRecyclerView() {
        rv_currentClasses?.setHasFixedSize(true)
        rv_currentClasses?.layoutManager = LinearLayoutManager(context)
        rv_currentClasses?.adapter = CurrentClassesAdapter(requireContext(), currentClasses)

        pb_checkForClasses?.hide()

    }

}