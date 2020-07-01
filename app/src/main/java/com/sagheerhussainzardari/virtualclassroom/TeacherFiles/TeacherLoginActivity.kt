package com.sagheerhussainzardari.virtualclassroom.TeacherFiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.R

class TeacherLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)
    }

    fun onBtnLogin_TeacherLoginActivityClicked(view: View) {
        showToastShort("On Login Teacher")

    }
}