package com.sagheerhussainzardari.virtualclassroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentLoginActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherLoginActivity


class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)


    }

    fun onLoginAsStudentClicked(view: View) {
        startActivity(Intent(this , StudentLoginActivity::class.java))
    }
    fun onLoginAsTeacherClicked(view: View) {
        startActivity(Intent(this , TeacherLoginActivity::class.java))
    }

    fun onTermsAndConditionsClicked(view: View) {}


}