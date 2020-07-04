package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.sagheerhussainzardari.easyandroid.CallBacks.AuthCallBack
import com.sagheerhussainzardari.easyandroid.CallBacks.RealtimeDatabaseCallBack
import com.sagheerhussainzardari.easyandroid.getDataFromFirebaseRealtimemDatabase
import com.sagheerhussainzardari.easyandroid.loginWithValidation
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_Students
import com.sagheerhussainzardari.virtualclassroom.R
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
        loginWithValidation(et_email_loginStudent, et_password_loginStudent, mAuth, this)
    }


    override fun onLoginSuccess() {
        getDataFromFirebaseRealtimemDatabase(DBRef_Students, this)

        startActivity(Intent(this, StudentHomeActivity::class.java))
    }

    override fun onLoginFailed(failureMessage: String) {
        showToastShort(failureMessage)

    }

    override fun onSignUpFailed(failureMessage: String) {}

    override fun onSignUpSuccess() {}


    override fun onDataGetFailure(failureMessage: String) {
        showToastShort(failureMessage)
    }

    override fun onDataGetSuccess(snapshot: DataSnapshot) {
        showToastShort(snapshot.toString())
    }

    override fun onDataStoredFailure(failureMessage: String) {}

    override fun onDataStoredSuccess() {}


}