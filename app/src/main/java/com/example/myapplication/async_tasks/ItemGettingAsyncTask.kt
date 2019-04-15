package com.example.myapplication.async_tasks

import android.os.AsyncTask
import android.widget.TextView
import com.example.myapplication.Repository
import com.example.myapplication.activities.NewsViewerActivity
import com.example.myapplication.database.NewsItem
import java.lang.ref.WeakReference

/*
class ItemGettingAsyncTask(private val content : WeakReference<TextView>, private val date : WeakReference<TextView>) : AsyncTask<Int, Void, NewsItem>() {
    override fun doInBackground(vararg params: Int?): NewsItem {
        return Repository.getNewsItemById(params[0] as Int)
    }

    override fun onPostExecute(newsItem: NewsItem) {
        val activity = content.get()?.context as NewsViewerActivity
        activity.newsItem = newsItem
        activity.title = newsItem.name
        content.get()?.text = newsItem.content
        date.get()?.text = newsItem.date
        if(newsItem.isFav)
            activity.setIconsAdded()
        else
            activity.setIconsNotAdded()
    }
}*/
