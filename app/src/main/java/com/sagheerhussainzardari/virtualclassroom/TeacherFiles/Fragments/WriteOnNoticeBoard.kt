package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R

class WriteOnNoticeBoard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_write_on_notice_board, container, false)
    }


}