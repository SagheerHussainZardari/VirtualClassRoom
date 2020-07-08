package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity

class TeacherLogoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_logout, container, false)
    }


    override fun onStart() {
        super.onStart()
        (activity as TeacherHomeActivity).logout()
    }
}