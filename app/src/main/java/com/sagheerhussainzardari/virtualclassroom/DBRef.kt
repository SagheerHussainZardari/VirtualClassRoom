package com.sagheerhussainzardari.virtualclassroom

import com.google.firebase.database.FirebaseDatabase

var DBRef_Base = FirebaseDatabase.getInstance().reference
var DBRef_ScheduledClasses = DBRef_Base.child("ScheduledClasses")
var DBRef_Students = DBRef_Base.child("Students")
var DBRef_Teachers = DBRef_Base.child("Teachers")
var DBRef_Assignments = DBRef_Base.child("Assignments")
var DBRef_Results = DBRef_Base.child("Results")
var DBRef_Attendence = DBRef_Base.child("Attendence")
var DBRef_CourseMaterial = DBRef_Base.child("CourseMaterial")

var DBRef_Subjects = DBRef_Base.child("Subjects")
var DBRef_LECTURES = DBRef_Base.child("Lectures")

var DBRef_Notes = DBRef_Base.child("Notes")

var DBRef_ChatStdToStd = DBRef_Base.child("ChatStdToStd")
