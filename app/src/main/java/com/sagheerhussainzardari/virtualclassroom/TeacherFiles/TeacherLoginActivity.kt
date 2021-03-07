package com.sagheerhussainzardari.virtualclassroom.TeacherFiles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.easyandroid.*
import com.sagheerhussainzardari.easyandroid.CallBacks.AuthCallBack
import com.sagheerhussainzardari.virtualclassroom.DBRef_Teachers
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StartScreenActivity
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentLoginActivity
import kotlinx.android.synthetic.main.activity_teacher_login.*
import kotlinx.android.synthetic.main.layout_forgotpasswordstudent.*

class TeacherLoginActivity : AppCompatActivity(), AuthCallBack {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)
    }

    fun onBtnLogin_TeacherLoginActivityClicked(view: View) {
        pb_teacherLoginActvity.show()
        loginWithValidation(
            et_email_loginTeacher,
            et_password_loginTeacher,
            StudentLoginActivity.mAuth,
            this
        )
    }

    override fun onBackPressed() {
        startActivity(Intent(this, StartScreenActivity::class.java))
    }

    override fun onLoginSuccess() {
        getTeacherDataFromDataBase(
            et_email_loginTeacher.text.toString().substringBefore('@').toLowerCase()
        )
    }

    private fun getTeacherDataFromDataBase(email: String) {

        DBRef_Teachers.child(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                onDataGetFailure(error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                onDataGetSuccess(snapshot)
            }
        })
    }

    fun onDataGetFailure(failureMessage: String) {
        pb_teacherLoginActvity.hide()

        showToastShort(failureMessage)
    }

    fun onDataGetSuccess(snapshot: DataSnapshot) {
        pb_teacherLoginActvity.hide()


        if (snapshot.hasChildren()) {
            val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
            val edit = sp.edit()

            edit.putBoolean("isAnyBodyLoggedIn", true)
            edit.putInt("accountType", 0)
            //saving current teacher data in sharedprefrence

            edit.putString("teacherName", snapshot.child("name").value.toString())
            edit.putString("teacherEmail", snapshot.child("email").value.toString())
            edit.putString("teacherFaculty", snapshot.child("faculty").value.toString())
            edit.putString("teacherDept", snapshot.child("dept").value.toString())

            edit.apply()

            startActivity(Intent(this, TeacherHomeActivity::class.java))
        }

    }


    override fun onLoginFailed(failureMessage: String) {
        pb_teacherLoginActvity.hide()
        showToastLong(failureMessage)
    }

    override fun onSignUpFailed(failureMessage: String) {}
    override fun onSignUpSuccess() {}
    fun onForgotPassword_TeacherLoginAcitivity(view: View) {

        layout_forgotpasswordteacher?.show()
        layout_forgotpassword_View.setOnClickListener {
            layout_forgotpasswordteacher?.hide()
        }
        layout_forgotpassword_LinearLayout.setOnClickListener { }
        btn_resetPassword_forgotpassword.setOnClickListener {
            if (isEmailValid(et_email_forgotpassword)) {
                mAuth.sendPasswordResetEmail(et_email_forgotpassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToastLong("Password Reset Link Sent!!\nCheck Your Inbox!")
                            layout_forgotpasswordteacher?.hide()
                        } else {
                            showToastLong("Password Reset Failed!\n${it.exception!!.localizedMessage}")
                        }
                    }
            }
        }

    }

}