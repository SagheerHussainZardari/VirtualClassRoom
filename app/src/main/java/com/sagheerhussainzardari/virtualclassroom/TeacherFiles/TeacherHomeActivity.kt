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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.sagheerhussainzardari.easyandroid.hide
import com.sagheerhussainzardari.easyandroid.show
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.*
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import kotlinx.android.synthetic.main.fragment_teacher_upload_attendence.*
import kotlinx.android.synthetic.main.fragment_teacher_upload_course_material.*
import kotlinx.android.synthetic.main.fragment_teacher_upload_results.*
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
                R.id.nav_writeOnNoticeBoard,
                R.id.nav_youtubeLecturesFragment
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
            showToastLong("PDF SELECTED CLICK UPLOAD BUTTON TO UPLOAD NOW....")
        }
    }


    private fun uploadDownloadUrl(downloadUrl: String, ref: DatabaseReference) {
        val baseRefForStoringClassInformation = ref
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode)

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

    fun uploadAssignment() {

        if (pdfUri != null) {
            showToastShort("Please Wait Uploading Assignment")
            pb_uploadingAssignment?.setOnClickListener { }
            pb_uploadingAssignment?.show()

            val filePath =
                "Assignments/$teacherFaculty/$teacherDept/${TeacherHomeFragment.currentClassSelected!!.subjectDegree}/${TeacherHomeFragment.currentClassSelected!!.subjectTime}/${TeacherHomeFragment.currentClassSelected!!.subjectBatch}/${TeacherHomeFragment.currentClassSelected!!.subjectGroup}/${TeacherHomeFragment.currentClassSelected!!.subjectCode}"
            mStorageRef.child(filePath).putFile(pdfUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                        uploadDownloadUrl(downloadUrl.result.toString(), DBRef_Assignments)
                        showToastShort("Assignment Uploaded Successfully!!!")
                        tv_chooseAssignment?.text = "Choose Assignment"
                        pb_uploadingAssignment?.hide()
                    }
                } else {
                    tv_chooseAssignment?.text = "Choose Assignment"
                    showToastShort("Failed To Upload Assignment\nTry Again!!")
                    pb_uploadingAssignment?.hide()
                }
            }
        } else {
            tv_chooseAssignment?.text = "Choose Assignment"
            showToastShort("Please Select A PDF File First Than Click Upload")
        }
    }


    fun uploadResults() {
        if (pdfUri != null) {
            showToastShort("Please Wait Uploading Results")
            pb_uploadingResult?.setOnClickListener { }
            pb_uploadingResult?.show()

            val filePath =
                "Results/$teacherFaculty/$teacherDept/${TeacherHomeFragment.currentClassSelected!!.subjectDegree}/${TeacherHomeFragment.currentClassSelected!!.subjectTime}/${TeacherHomeFragment.currentClassSelected!!.subjectBatch}/${TeacherHomeFragment.currentClassSelected!!.subjectGroup}/${TeacherHomeFragment.currentClassSelected!!.subjectCode}"
            mStorageRef.child(filePath).putFile(pdfUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                        uploadDownloadUrl(downloadUrl.result.toString(), DBRef_Results)
                        showToastShort("Result Uploaded Successfully!!!")
                        tv_chooseResult?.text = "Choose Result"
                        pb_uploadingResult?.hide()
                    }
                } else {
                    tv_chooseResult?.text = "Choose Result"
                    showToastShort("Failed To Upload Results\nTry Again!!")
                    pb_uploadingResult?.hide()
                }
            }
        } else {
            tv_chooseResult?.text = "Choose Result"
            showToastShort("Please Select A PDF File First Than Click Upload")
        }
    }

    fun uploadAttendence() {
        if (pdfUri != null) {
            showToastShort("Please Wait Uploading Attendence")
            pb_uploadingAttendence?.setOnClickListener { }
            pb_uploadingAttendence?.show()

            val filePath =
                "Attendence/$teacherFaculty/$teacherDept/${TeacherHomeFragment.currentClassSelected!!.subjectDegree}/${TeacherHomeFragment.currentClassSelected!!.subjectTime}/${TeacherHomeFragment.currentClassSelected!!.subjectBatch}/${TeacherHomeFragment.currentClassSelected!!.subjectGroup}/${TeacherHomeFragment.currentClassSelected!!.subjectCode}"
            mStorageRef.child(filePath).putFile(pdfUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                        uploadDownloadUrl(downloadUrl.result.toString(), DBRef_Attendence)
                        showToastShort("Attendence Uploaded Successfully!!!")
                        tv_chooseAttendence?.text = "Choose Attendence"
                        pb_uploadingAttendence?.hide()
                    }
                } else {
                    tv_chooseAttendence?.text = "Choose Attendence"
                    showToastShort("Failed To Upload Attendence\nTry Again!!")
                    pb_uploadingAttendence?.hide()
                }
            }
        } else {
            tv_chooseAttendence?.text = "Choose Attendece"
            showToastShort("Please Select A PDF File First Than Click Upload")
        }
    }

    fun uploadCourseMaterial() {
        if (pdfUri != null) {
            showToastShort("Please Wait Uploading Course Material")
            pb_uploadingCourseMaterial?.setOnClickListener { }
            pb_uploadingCourseMaterial?.show()

            val filePath =
                "CourseMaterial/$teacherFaculty/$teacherDept/${TeacherHomeFragment.currentClassSelected!!.subjectDegree}/${TeacherHomeFragment.currentClassSelected!!.subjectTime}/${TeacherHomeFragment.currentClassSelected!!.subjectBatch}/${TeacherHomeFragment.currentClassSelected!!.subjectGroup}/${TeacherHomeFragment.currentClassSelected!!.subjectCode}"
            mStorageRef.child(filePath).putFile(pdfUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result!!.storage.downloadUrl.addOnCompleteListener { downloadUrl ->
                        uploadDownloadUrl(downloadUrl.result.toString(), DBRef_CourseMaterial)
                        showToastShort("Uploaded Course Material Successfully!!!")
                        tv_chooseCourseMaterial?.text = "Choose Course Material"
                        pb_uploadingCourseMaterial?.hide()
                    }
                } else {
                    tv_chooseCourseMaterial?.text = "Choose Course"
                    showToastShort("Failed To Upload Course Material\nTry Again!!")
                    pb_uploadingCourseMaterial?.hide()
                }
            }
        } else {
            tv_chooseCourseMaterial?.text = "Choose Course Material"
            showToastShort("Please Select A PDF File First Than Click Upload")
        }
    }

    fun uploadYoutubeLink() {
        showToastShort("uploading link");
    }


}