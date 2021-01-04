package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models

data class CurrentClassModel(
    var classSubject: String,
    var classTeacher: String,
    var classTime: String,
    var classDate: String,
    var classZoomID: String,
    var classZoomPassword: String,
    var classZoomLink: String
)