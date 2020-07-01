package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.R

class StudentLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlogin)


    }

    fun onBtnLogin_StudentLoginActivityClicked(view: View) {
        showToastShort("On Login Student")
    }
}