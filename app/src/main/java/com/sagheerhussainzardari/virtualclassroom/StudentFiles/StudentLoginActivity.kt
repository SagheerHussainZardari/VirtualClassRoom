package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.*
import com.sagheerhussainzardari.easyandroid.CallBacks.AuthCallBack
import com.sagheerhussainzardari.virtualclassroom.DBRef_Students
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StartScreenActivity
import kotlinx.android.synthetic.main.activity_student_login.*
import kotlinx.android.synthetic.main.layout_forgotpassword.*

class StudentLoginActivity : AppCompatActivity(), AuthCallBack {

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
        getStudentDataFromDataBase(
            et_email_loginStudent.text.toString().substringBefore('@').toLowerCase()
        )
    }

    private fun getStudentDataFromDataBase(email: String) {
        DBRef_Students.child(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                onDataGetFailure(error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                onDataGetSuccess(snapshot)
            }
        })
    }

    override fun onLoginFailed(failureMessage: String) {
        pb_studentLoginActvity.hide()
        showToastShort(failureMessage)
    }

    override fun onSignUpFailed(failureMessage: String) {}
    override fun onSignUpSuccess() {}
    fun onDataGetFailure(failureMessage: String) {
        showToastShort(failureMessage)
        pb_studentLoginActvity.hide()
    }

    fun onDataGetSuccess(snapshot: DataSnapshot) {

        showToastShort(snapshot.toString())

        pb_studentLoginActvity.hide()

        if (snapshot.hasChildren()) {
            val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
            val edit = sp.edit()

            edit.putBoolean("isAnyBodyLoggedIn", true)
            edit.putInt("accountType", 1)
            edit.putString("studentRollNumber", snapshot.child("rollNumber").value.toString())
            edit.putString("studentEmail", snapshot.child("email").value.toString())
            edit.putString("studentName", snapshot.child("name").value.toString())
            edit.apply()

            startActivity(Intent(this, StudentHomeActivity::class.java))
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, StartScreenActivity::class.java))
    }

    fun onForgotPassword_StudentLoginAcitivity(view: View) {
        layout_forgotpassword.show()
        layout_forgotpassword_View.setOnClickListener {
            layout_forgotpassword.hide()
        }

        layout_forgotpassword_LinearLayout.setOnClickListener { }

        btn_resetPassword_forgotpassword.setOnClickListener {
            if (isEmailValid(et_email_forgotpassword)) {
                mAuth.sendPasswordResetEmail(et_email_forgotpassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToastLong("Password Reset Link Sent!!\nCheck Your Inbox!")
                            layout_forgotpassword.hide()
                        } else {
                            showToastLong("Password Reset Failed!\n${it.exception!!.localizedMessage}")
                        }
                    }
            }
        }

    }

}