package com.sagheerhussainzardari.virtualclassroom

import com.google.firebase.database.FirebaseDatabase

var DBRef_Base = FirebaseDatabase.getInstance().reference
var DBRef_Students = DBRef_Base.child("Students")
