package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.TeacherHomeFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments.YoutubeLecturesFragment
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.YoutubeLectures
import kotlinx.android.synthetic.main.layout_recycler_teacherclasses.view.*
import kotlinx.android.synthetic.main.recycler_lecture_view.view.*

public class YoutubeLecturesAdapter(
    var context: Context,
    var list: ArrayList<YoutubeLectures>,
    var teacherYoutubeLecturesFragment: YoutubeLecturesFragment
) : RecyclerView.Adapter<YoutubeLecturesAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.recycler_lecture_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.view.tv_title_lecture.text = list[position].title
            holder.view.btn_delete_lecture.setOnClickListener {
                teacherYoutubeLecturesFragment.onLectureDelete(list[position].key)
            }
    }


}