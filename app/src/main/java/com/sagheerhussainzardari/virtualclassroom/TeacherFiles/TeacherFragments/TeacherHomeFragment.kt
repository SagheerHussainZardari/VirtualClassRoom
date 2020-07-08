package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.virtualclassroom.DBRef_Teachers
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters.ClassesTaughtByThisTeacherAdapter
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_teacher_home.*

class TeacherHomeFragment : Fragment() {

    companion object {
        var classesTaughtByThisTeacherList = ArrayList<ClassesTaughtByThisTeacherModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_teacher_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getClassesList()

    }

    private fun getClassesList() {
        DBRef_Teachers
            .child(TeacherHomeActivity.teacherEmail.substringBefore('@').toLowerCase())
            .child("classes")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    onDataGetSuccess(snapshot)
                }
            })
    }

    private fun onDataGetSuccess(snapshot: DataSnapshot) {
        classesTaughtByThisTeacherList.clear()

        if (snapshot.hasChildren()) {
            var subjectName = ""
            var subjectTime = ""
            var subjectBatch = ""
            var subjectFaculty = TeacherHomeActivity.teacherFaculty
            var subjectDept = TeacherHomeActivity.teacherDept
            var subjectDegree = ""
            var subjectGroup = ""

            for (Degree in snapshot.children) {
                subjectDegree = Degree.key.toString()
                for (Time in Degree.children) {
                    subjectTime = Time.key.toString()
                    for (batch in Time.children) {
                        subjectBatch = batch.key.toString()
                        for (group in batch.children) {
                            subjectGroup = group.key.toString()
                            for (subject in group.children) {
                                classesTaughtByThisTeacherList.add(
                                    ClassesTaughtByThisTeacherModel(
                                        subject.child("subjectName").value.toString(),
                                        subjectTime,
                                        subjectBatch,
                                        subjectFaculty,
                                        subjectDept,
                                        subjectDegree,
                                        subjectGroup
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Log.d("a", "SagheerHussain: ${classesTaughtByThisTeacherList}")

            setUpRecyclerView()

        } else {
            context?.showToastLong("You Have No Classes To Teach")
        }
    }

    private fun setUpRecyclerView() {
        rv_teacherclasses.setHasFixedSize(true)
        rv_teacherclasses.layoutManager = LinearLayoutManager(context)
        rv_teacherclasses.adapter = ClassesTaughtByThisTeacherAdapter(
            requireContext(),
            classesTaughtByThisTeacherList
        )
    }
}