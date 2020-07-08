package com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity

class StudentCheckForClassesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_check_for_classes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(
            "a",
            "SagheerData: Check Classes For\n ${StudentHomeActivity.studentFaculity}-${StudentHomeActivity.studentDept}-${StudentHomeActivity.studentDegree}-${StudentHomeActivity.studentTime}-${StudentHomeActivity.studentBatch}-${StudentHomeActivity.studentGroup}"
        )
    }

}