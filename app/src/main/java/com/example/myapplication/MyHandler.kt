package com.example.myapplication

import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.example.myapplication.activities.NewsViewerActivity

class MyHandler(private val activity: NewsViewerActivity) : Handler(){
    override fun handleMessage(msg: Message) {
        when(msg.what){
            NewsViewerActivity.ADDED -> activity.setIconsAdded()
            NewsViewerActivity.REMOVED -> activity.setIconsNotAdded()
            else -> Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
