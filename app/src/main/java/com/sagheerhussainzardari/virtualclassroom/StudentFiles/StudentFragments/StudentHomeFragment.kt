package com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentFragments

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

            (activity as StudentHomeActivity).openFragment(StudentCheckForClassesFragment())
        }

    }
}