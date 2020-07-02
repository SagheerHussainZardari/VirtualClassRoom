package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sagheerhussainzardari.virtualclassroom.R

class StudentLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlogin)


    }

    fun onBtnLogin_StudentLoginActivityClicked(view: View) {
        startActivity(Intent(this, StudentHomeActivity::class.java))
    }
}