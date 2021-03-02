package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sagheerhussainzardari.virtualclassroom.DBRef_LECTURES
import com.sagheerhussainzardari.virtualclassroom.R
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Adapters.YoutubeLecturesAdapter
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Models.YoutubeLectures
import com.sagheerhussainzardari.virtualclassroom.TeacherFiles.TeacherHomeActivity
import kotlinx.android.synthetic.main.fragment_youtube_lectures.*


class YoutubeLecturesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_lectures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerLectures()


        btn_upload_youtube_link.setOnClickListener {


            var link = et_YoutubeLink.text.toString();
            var title = et_title.text.toString();

            if(link.isEmpty() || title.isEmpty()){
                Toast.makeText(requireContext(), "Link Should Not Be Empty", Toast.LENGTH_SHORT).show()
            }else{
                (activity as TeacherHomeActivity).uploadYoutubeLink(link , title);
                et_YoutubeLink.setText("");
                et_title.setText("");
                setUpRecyclerLectures()

            }



        }
    }

    private fun setUpRecyclerLectures() {

        val baseRefForStoringClassInformation = DBRef_LECTURES
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode)

        baseRefForStoringClassInformation.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var listOfLectures = ArrayList<YoutubeLectures>()

                for(document in snapshot.children){
                    listOfLectures.add(
                        YoutubeLectures(
                            document.child("title").value.toString(),
                            document.child("link").value.toString(),
                            document.key.toString()
                    )
                    )
                }

                rv_youtube_lectures?.setHasFixedSize(true)
                rv_youtube_lectures?.layoutManager = LinearLayoutManager(context)
                rv_youtube_lectures?.adapter = YoutubeLecturesAdapter(requireContext(), listOfLectures , YoutubeLecturesFragment())

            }
        })


    }


    fun onLectureDelete(key: String) {

        val baseRefForStoringClassInformation = DBRef_LECTURES
            .child(TeacherHomeActivity.teacherFaculty)
            .child(TeacherHomeActivity.teacherDept)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectDegree.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectTime)
            .child(TeacherHomeFragment.currentClassSelected!!.subjectBatch.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectGroup.toUpperCase())
            .child(TeacherHomeFragment.currentClassSelected!!.subjectCode)

        baseRefForStoringClassInformation.child(key).removeValue();

        setUpRecyclerLectures();
    }

}