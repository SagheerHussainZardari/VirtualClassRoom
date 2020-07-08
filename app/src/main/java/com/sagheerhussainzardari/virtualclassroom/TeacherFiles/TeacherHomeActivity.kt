package com.sagheerhussainzardari.virtualclassroom.TeacherFiles

import android.content.Context
import android.content.Intent
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
import com.sagheerhussainzardari.virtualclassroom.R
import kotlinx.android.synthetic.main.nav_header_teacher.view.*

class TeacherHomeActivity : AppCompatActivity() {

    companion object {
        var mAuth = FirebaseAuth.getInstance()
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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_teacherhomefragment), drawerLayout)
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
}