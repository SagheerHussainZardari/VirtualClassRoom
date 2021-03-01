package com.sagheerhussainzardari.virtualclassroom.TeacherFiles.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sagheerhussainzardari.easyandroid.showToastShort
import com.sagheerhussainzardari.virtualclassroom.R
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

        btn_upload_youtube_link.setOnClickListener {


            var link = et_YoutubeLink.text.toString();
            var title = et_title.text.toString();

            if(link.isEmpty() || title.isEmpty()){
                Toast.makeText(requireContext(), "Link Should Not Be Empty", Toast.LENGTH_SHORT).show()
            }else{

                (activity as TeacherHomeActivity).uploadYoutubeLink(link , title);
                et_YoutubeLink.setText("");
                et_title.setText("");
            }
        }
    }
}