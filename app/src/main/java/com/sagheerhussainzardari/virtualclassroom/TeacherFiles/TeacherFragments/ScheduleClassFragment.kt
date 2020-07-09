package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.R
import kotlinx.android.synthetic.main.fragment_teacher_scheduleclass.*

class ScheduleClassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_scheduleclass, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_addClass_ScheduleClassFragmentTeacher.setOnClickListener {
            val zoonID = et_zoomID.text.toString()
            val zoonPassword = et_zoomPassword.text.toString()
            val zoonLink = et_zoomLink.text.toString()

            context?.showToastShort("$zoonID , $zoonPassword , $zoonLink")
        }
    }

}