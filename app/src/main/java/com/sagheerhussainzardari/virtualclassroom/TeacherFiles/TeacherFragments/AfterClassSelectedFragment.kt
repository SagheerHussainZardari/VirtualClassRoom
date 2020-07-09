package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R
import kotlinx.android.synthetic.main.fragment_teacher_after_class_selected.*

class AfterClassSelectedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_after_class_selected, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_scheduleNextClass_AfterClassSelectedTeacher.setOnClickListener {

        }
    }

}