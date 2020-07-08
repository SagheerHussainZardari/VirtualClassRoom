package com.sagheerhussainzardari.virtualclassroom.StudentFiles.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.easyandroid.showToastLong
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.CurrentClassModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import kotlinx.android.synthetic.main.layout_recycler_currentclasses.view.*

class CurrentClassesAdapter(
    var context: Context,
    var currectClassesList: ArrayList<CurrentClassModel>
) : RecyclerView.Adapter<CurrentClassesAdapter.MyViewModel>() {

    class MyViewModel(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        return MyViewModel(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_recycler_currentclasses, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return currectClassesList.size
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {

        holder.view.tv_classTeacherName_recycler_currentclassess.text =
            currectClassesList[position].classTeacher
        holder.view.tv_classNameAndBatch_recycler_currentclassess.text =
            StudentHomeActivity.studentBatch + " - " + currectClassesList[position].classSubject
        holder.view.tv_classDateTime_recycler_currentclassess.text =
            currectClassesList[position].classDate + " - " + currectClassesList[position].classTime
        holder.view.tv_classZoomID_recycler_currentclassess.text =
            "ZoomID: " + currectClassesList[position].classZoomID
        holder.view.tv_classZoomPassword_recycler_currentclassess.text =
            "Zoom Password: " + currectClassesList[position].classZoomPassword

        holder.view.btn_joinMeeting_recycler_currentclassess.setOnClickListener {
            context?.showToastLong("visit link")
        }

    }

}