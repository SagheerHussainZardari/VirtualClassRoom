package com.sagheerhussainzardari.virtualclassroom.TeacherFiles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sagheerhussainzardari.virtualclassroom.R

class TeacherLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)
    }


    fun onBtnLogin_TeacherLoginActivityClicked(view: View) {
        startActivity(Intent(this, TeacherHomeActivity::class.java))

    }
}