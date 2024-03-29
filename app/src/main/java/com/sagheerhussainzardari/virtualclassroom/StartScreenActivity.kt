package com.sagheerhussainzardari.virtualclassroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentLoginActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherLoginActivity


class StartScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

    }

    fun onLoginAsStudentClicked(view: View) {
        startActivity(Intent(this, StudentLoginActivity::class.java))
    }

    fun onLoginAsTeacherClicked(view: View) {
        startActivity(Intent(this, TeacherLoginActivity::class.java))
    }

    fun onTermsAndConditionsClicked(view: View) {}


    override fun onResume() {
        super.onResume()

        //check if there is User

        val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)

        var isAnyBodyLoggedIn = sp.getBoolean("isAnyBodyLoggedIn", false)
        var accountType = sp.getInt("accountType", -1)

        if (isAnyBodyLoggedIn) {
            if (accountType == 1) {
                startActivity(Intent(this, StudentHomeActivity::class.java))
            } else if (accountType == 0) {
                startActivity(Intent(this, TeacherHomeActivity::class.java))
            }
        }


    }

    override fun onBackPressed() {
        finishAffinity()
    }
}