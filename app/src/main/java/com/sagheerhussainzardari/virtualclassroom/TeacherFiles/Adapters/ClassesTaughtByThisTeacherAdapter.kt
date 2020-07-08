package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.ClassesTaughtByThisTeacherModel
import kotlinx.android.synthetic.main.layout_recycler_teacherclasses.view.*

public class ClassesTaughtByThisTeacherAdapter(
    var context: Context,
    var list: ArrayList<ClassesTaughtByThisTeacherModel>
) : RecyclerView.Adapter<ClassesTaughtByThisTeacherAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_recycler_teacherclasses, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_subjectNameTeacherClasses.text = list[position].subjectName
    }

}