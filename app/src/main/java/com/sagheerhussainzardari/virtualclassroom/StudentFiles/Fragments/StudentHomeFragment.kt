package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import kotlinx.android.synthetic.main.fragment_student_home.*

class StudentHomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        card_checkForClasses_StudentHomeFragment.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_studentCheckForClassesFragment)
        }

        card_student_assignment.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_studentAssignment)
        }

        card_results.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_results)
        }

        card_attendence.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_attendence)
        }

        card_course_material.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_coursesMaterial)
        }

//        card_chat_with_teacher.setOnClickListener {
//            (activity as StudentHomeActivity).openFragment(R.id.nav_chatWithTeacher)
//        }

        card_chat_with_class_fellow.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_chatWithClassFellows)
        }

        card_chat_with_class_fellow.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_chatWithClassFellows)
        }

        card_digital_notice_board.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_digitalNoticeBoard)
        }

        card_student_lectures.setOnClickListener {
            (activity as StudentHomeActivity).openFragment(R.id.nav_previousLecturesFragment)

        }
    }

}