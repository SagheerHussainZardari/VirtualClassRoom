package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Fragments.PreviousLecturesFragment
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.Models.YoutubeLecturesModel
import com.sagheerhussainzardari.virtualclassroom.StudentFiles.StudentHomeActivity
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.YoutubeLecturesFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.YoutubeLectures
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.layout_recycler_teacherclasses.view.*
import kotlinx.android.synthetic.main.recycler_lecture_view.view.*
import kotlinx.android.synthetic.main.recycler_lecture_view.view.tv_title_lecture
import kotlinx.android.synthetic.main.recycler_view_student_lectures.view.*

public class YoutubePreviousLecturesAdapter(
    var context: Context,
    var list: ArrayList<YoutubeLecturesModel>,
    var studentYoutubeLecture: PreviousLecturesFragment
) : RecyclerView.Adapter<YoutubePreviousLecturesAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_student_lectures, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_title_lecture.text = list[position].title


        holder.view.btn_play.setOnClickListener {
            (context as StudentHomeActivity).openLink(list[position].link)
        }


    }


}