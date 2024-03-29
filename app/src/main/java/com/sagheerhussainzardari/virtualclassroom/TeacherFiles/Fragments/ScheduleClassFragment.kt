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
import com.sagheerhussainzardari.easyandroid.hide
import com.sagheerhussainzardari.easyandroid.show
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_ScheduledClasses
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_student_chat_with_class_fellows.*
import kotlinx.android.synthetic.main.fragment_teacher_scheduleclass.*

class ScheduleClassFragment : Fragment() {

    companion object {
        lateinit var baseRefForStoringClassInformation: DatabaseReference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_scheduleclass, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseRefForStoringClassInformation = DBRef_ScheduledClasses
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode)


        setUpCurrentlyAddedClass()

        btn_addClass_ScheduleClassFragmentTeacher?.setOnClickListener {
            val zoomID = et_zoomID.text.toString()
            val zoomPassword = et_zoomPassword.text.toString()
            val zoomLink = et_zoomLink.text.toString()
            val zoomTime = et_zoomTime.text.toString()
            val zoomDate = et_zoomDate.text.toString()

            if (zoomID.isNotEmpty()) {
                if (zoomPassword.isNotEmpty()) {
                    if (zoomLink.isNotEmpty()) { /*Patterns.WEB_URL.matcher(zoomLink).matches()*/
                        if (zoomTime.isNotEmpty()) {
                            if (zoomDate.isNotEmpty()) {
                                pb_schceduleClass?.show()

                                baseRefForStoringClassInformation.child("classDate")
                                    .setValue(zoomDate)
                                baseRefForStoringClassInformation.child("classTime")
                                    .setValue(zoomTime)
                                baseRefForStoringClassInformation.child("classZoomID")
                                    .setValue(zoomID)
                                baseRefForStoringClassInformation.child("classZoomPassword")
                                    .setValue(zoomPassword)
                                baseRefForStoringClassInformation.child("classZoomLink")
                                    .setValue(zoomLink)
                                baseRefForStoringClassInformation.child("classDate")
                                    .setValue(zoomDate)
                                baseRefForStoringClassInformation.child("classSubject")
                                    .setValue(TeacherHomeFragment.currentClassSelected!!.subjectName)
                                baseRefForStoringClassInformation.child("classTeacher")
                                    .setValue(TeacherHomeActivity.teacherName)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            context?.showToastShort("Class Scheduled Complete!!!")
                                            setUpCurrentlyAddedClass()
                                            pb_schceduleClass?.hide()
                                        } else {
                                            pb_schceduleClass?.hide()
                                            context?.showToastShort("Class Not Added Something Went Wrong")
                                        }
                                    }

                            } else {
                                et_zoomDate.error = "Zoom Date Must Not Be Empty!!!"
                                et_zoomDate.requestFocus()
                            }
                        } else {
                            et_zoomTime.error = "Zoom Time Must Not Be Empty!!!"
                            et_zoomTime.requestFocus()
                        }
                    } else {
                        et_zoomLink.error = "Provide A Valid Zoom Link"
                        et_zoomLink.requestFocus()
                    }
                } else {
                    et_zoomPassword.error = "Zoom Password Must Not Be Empty!!!"
                    et_zoomPassword.requestFocus()
                }
            } else {
                et_zoomID.error = "Zoom ID Must Not Be Empty!!!"
                et_zoomID.requestFocus()
            }

        }
    }

    private fun setUpCurrentlyAddedClass() {

        baseRefForStoringClassInformation
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        tv_previousClass?.show()
                        tv_previousClass?.text =
                            snapshot.child("classDate").value.toString() + " => " + snapshot.child("classTime").value.toString()
                    } else {
                        tv_previousClass?.hide()
                        context?.showToastLong("No Class Scheduled Yet!!!")
                    }
                }
            })

    }

}