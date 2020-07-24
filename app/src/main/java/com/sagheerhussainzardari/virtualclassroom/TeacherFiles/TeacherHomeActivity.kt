package com.sagheerhussainzardari.virtualclassroom.TeacherFiles

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.sagheerhussainzardari.easyandroid.hide
import com.sagheerhussainzardari.easyandroid.show
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.DBRef_Assignments
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import kotlinx.android.synthetic.main.fragment_teacher_uploadassignment.*
import kotlinx.android.synthetic.main.nav_header_teacher.view.*
import java.io.File


class TeacherHomeActivity : AppCompatActivity() {


    companion object {
        var mAuth = FirebaseAuth.getInstance()
        var mStorageRef = FirebaseStorage.getInstance().reference
        var currentClassSelected: ClassesTaughtByThisTeacherModel? = null
        var pdfUri: Uri? = null
        var pdfUrl: String = ""
        var teacherName = ""
        var teacherEmail = ""
        var teacherFaculty = ""
        var teacherDept = ""
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout_teacher)
        val navView: NavigationView = findViewById(R.id.nav_view_teacher)
        val navController = findNavController(R.id.nav_host_fragment_teacher)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_teacherHomeFragment,
                R.id.nav_teacherAfterClassSelectedFragment,
                R.id.nav_teacherScheduleClassFragment,
                R.id.nav_teacherUploadAssignmentFragment,
                R.id.nav_teacherUploadResults,
                R.id.nav_uploadAttendence,
                R.id.nav_uploadCourseMaterial,
                R.id.nav_chatWithStudents,
                R.id.nav_writeOnNoticeBoard
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setUpUserDetails()

    }



    private fun setUpUserDetails() {
        val navView: NavigationView = findViewById(R.id.nav_view_teacher)

        val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)

        teacherName = sp.getString("teacherName", "").toString()
        teacherEmail = sp.getString("teacherEmail", "").toString()
        teacherFaculty = sp.getString("teacherFaculty", "").toString()
        teacherDept = sp.getString("teacherDept", "").toString()

        //setup name roll and email in header
        navView.getHeaderView(0).tv_teacherName.text = teacherName
        navView.getHeaderView(0).tv_teacherEmail.text = teacherEmail

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_teacher)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logout() {
        mAuth.signOut()

        val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putBoolean("isAnyBodyLoggedIn", false)
        edit.apply()
        startActivity(Intent(this, TeacherLoginActivity::class.java))
    }

    fun openFragment(fragment: Int) {
        val navController = findNavController(R.id.nav_host_fragment_teacher)
        navController.navigate(fragment)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {

            pdfUri = data.data
            val file = File(pdfUri!!.path)

//            var btn_chooseAssignment = findViewById<TextView>(R.id.tv_chooseAssignment)
            tv_chooseAssignment.text = data.dataString

        } else {
            showToastShort("Please Select a PDF File!!!")
            pb_uploadingAssignment.hide()
        }
    }

    fun uploadAssignment() {

        if (pdfUri != null) {
            showToastShort("Please Wait Uploading Assignment")
            pb_uploadingAssignment.setOnClickListener { }
            pb_uploadingAssignment.show()

            val filePath =
                "Assignments/$teacherFaculty/$teacherDept/${TeacherHomeFragment.currentClassSelected!!.subjectDegree}/${TeacherHomeFragment.currentClassSelected!!.subjectTime}/${TeacherHomeFragment.currentClassSelected!!.subjectBatch}/${TeacherHomeFragment.currentClassSelected!!.subjectGroup}/${TeacherHomeFragment.currentClassSelected!!.subjectName}"
            mStorageRef.child(filePath).putFile(pdfUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                        uploadDownloadUrl(downloadUrl.result.toString())
                        showToastShort("Assignment Uploaded Successfully!!!")
                        tv_chooseAssignment.text = "Choose Assignment"
                        pb_uploadingAssignment.hide()
                    }
                } else {
                    tv_chooseAssignment.text = "Choose Assignment"
                    showToastShort("Failed To Upload Assignment\nTry Again!!")
                    pb_uploadingAssignment.hide()
                }
            }
        } else {
            tv_chooseAssignment.text = "Choose Assignment"
            showToastShort("Please Select A PDF File First Than Click Upload")
        }
    }

    private fun uploadDownloadUrl(downloadUrl: String) {
        val baseRefForStoringClassInformation = DBRef_Assignments
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectName)

        baseRefForStoringClassInformation.child("downloadUrl").setValue(downloadUrl)
    }


    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_teacher)

        if (navController.currentDestination!!.id != R.id.nav_teacherHomeFragment) {
            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }

    fun selectPdfFromFiles() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, 200)
    }


}