package com.sagheerhussainzardari.virtualclassroom

import com.google.firebase.database.FirebaseDatabase

var DBRef_Base = FirebaseDatabase.getInstance().reference
var DBRef_ScheduledClasses = DBRef_Base.child("ScheduledClasses")
var DBRef_Students = DBRef_Base.child("Students")
var DBRef_Teachers = DBRef_Base.child("Teachers")
var DBRef_Assignments = DBRef_Base.child("Assignments")
