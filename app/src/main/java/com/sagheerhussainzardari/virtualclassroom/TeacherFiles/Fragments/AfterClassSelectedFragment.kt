package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_teacher_afterclassselected.*

class AfterClassSelectedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_afterclassselected, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_scheduleNextClass_AfterClassSelectedTeacher.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_teacherScheduleClassFragment)
        }

        card_UploadAssignment_AfterClassSelectedTeacher.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_teacherUploadAssignmentFragment)
        }

        card_uploadResult_teacherr.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_teacherUploadResults)
        }

        card_upload_attendence.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_uploadAttendence)
        }

        card_upload_course_material.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_uploadCourseMaterial)
        }

//        card_chat_with_students.setOnClickListener {
//            (activity as TeacherHomeActivity).openFragment(R.id.nav_chatWithStudents)
//        }

        card_write_on_noticeboard.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_writeOnNoticeBoard)
        }

        card_UploadYoutubeLectures_AfterClassSelectedTeacher.setOnClickListener {
            (activity as TeacherHomeActivity).openFragment(R.id.nav_youtubeLecturesFragment)
        }


    }

}