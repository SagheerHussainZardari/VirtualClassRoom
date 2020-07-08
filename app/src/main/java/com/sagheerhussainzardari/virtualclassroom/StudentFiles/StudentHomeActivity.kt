package com.sagheerhussainzardari.virtualclassroom.StudentFiles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sagheerhussainzardari.virtualclassroom.R
import kotlinx.android.synthetic.main.nav_header_student.view.*

class StudentHomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        var mAuth = FirebaseAuth.getInstance()

        var studentEmail = ""
        var studentRollNumber = ""
        var studentName = ""
        var studentBatch = ""
        var studentTime = ""
        var studentDegree = ""
        var studentDept = ""
        var studentFatherName = ""
        var studentFaculity = ""
        var studentGroup = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_studenthomefragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        setUpUserDetails()

    }

    private fun setUpUserDetails() {
        val navView: NavigationView = findViewById(R.id.nav_view)

        val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)

        studentRollNumber = sp.getString("studentRollNumber", "").toString()
        studentEmail = sp.getString("studentEmail", "").toString()
        studentName = sp.getString("studentName", "").toString()
        studentBatch = sp.getString("studentBatch", "").toString()
        studentTime = sp.getString("studentTime", "").toString()
        studentDegree = sp.getString("studentDegree", "").toString()
        studentDept = sp.getString("studentDept", "").toString()
        studentFatherName = sp.getString("studentFatherName", "").toString()
        studentFaculity = sp.getString("studentFaculity", "").toString()
        studentGroup = sp.getString("studentGroup", "").toString()

        //setup name roll and email in header
        navView.getHeaderView(0).tv_studentRollNumber.text = "$studentName ($studentRollNumber)"
        navView.getHeaderView(0).tv_studentEmail.text = studentEmail

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openFragment(fragment: Fragment) {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.nav_studentCheckForClassesFragment)
    }

    fun logout() {
        mAuth.signOut()

        val sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putBoolean("isAnyBodyLoggedIn", false)
        edit.apply()

        startActivity(Intent(this, StudentLoginActivity::class.java))
    }


    override fun onBackPressed() {

        val navController = findNavController(R.id.nav_host_fragment)

        if (navController.currentDestination!!.id != R.id.nav_studenthomefragment) {
            super.onBackPressed()
        } else {
            finishAffinity()
        }
    }

}