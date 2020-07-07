package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.sagheerhussainzardari.easyandroid.*
import com.sagheerhussainzardari.easyandroid.CallBacks.AuthCallBack
import com.sagheerhussainzardari.easyandroid.CallBacks.RealtimeDatabaseCallBack
import com.sagheerhussainzardari.virtualclassroom.DBRef_Students
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StartScreenActivity
import kotlinx.android.synthetic.main.activity_student_login.*

class StudentLoginActivity : AppCompatActivity(), AuthCallBack, RealtimeDatabaseCallBack {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)
    }


    fun onLoginStudentClicked(view: View) {
        pb_studentLoginActvity.setOnClickListener { }
        pb_studentLoginActvity.show()
        //Going For Login
        loginWithValidation(et_email_loginStudent, et_password_loginStudent, mAuth, this)
    }


    override fun onLoginSuccess() {
        //get Students List and Check Is User Exits Or not
        getDataFromFirebaseRealtimemDatabase(DBRef_Students, this)
    }
    override fun onLoginFailed(failureMessage: String) {
        pb_studentLoginActvity.hide()
        showToastShort(failureMessage)
    }
    override fun onSignUpFailed(failureMessage: String) {}
    override fun onSignUpSuccess() {}
    override fun onDataGetFailure(failureMessage: String) {
        showToastShort(failureMessage)
        pb_studentLoginActvity.hide()
    }
    override fun onDataGetSuccess(snapshot: DataSnapshot) {
        val email = et_email_loginStudent.text.toString().substringBefore('@').toLowerCase()
        var isStudent = false
        var studentRollNumber = ""
        for (document in snapshot.children) {
            if (document.value.toString().toLowerCase() == email) {
                isStudent = true
                studentRollNumber = document.key.toString()
            }
        }

        if (isStudent) {
            pb_studentLoginActvity.hide()

            val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
            val edit = sp.edit()

            edit.putBoolean("isAnyBodyLoggedIn", true)
            edit.putInt("accountType", 1)
            edit.putString("studentRollNumber", studentRollNumber)
            edit.putString("studentEmail", et_email_loginStudent.text.toString().toLowerCase())
            edit.apply()

            startActivity(Intent(this, StudentHomeActivity::class.java))
        } else {
            pb_studentLoginActvity.hide()
            mAuth.signOut()
            showToastShort("You Are Not A Student!")
        }

    }

    override fun onDataStoredFailure(failureMessage: String) {}
    override fun onDataStoredSuccess() {}


    override fun onBackPressed() {
        startActivity(Intent(this, StartScreenActivity::class.java))
    }

}