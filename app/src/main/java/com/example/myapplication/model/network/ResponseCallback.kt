package com.example.myapplication.model.network

import android.support.v4.widget.SwipeRefreshLayout
import com.example.myapplication.model.Repository
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.getDaysAgo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.net.UnknownHostException

class ResponseCallback(private val swiperReference : WeakReference<SwipeRefreshLayout>? = null) : Callback<NewsListResponse>{
    private val time = getDaysAgo(500).time

    override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
        if (t !is UnknownHostException)
            throw t
        if(swiperReference != null){
            swiperReference.get()?.isRefreshing = true
            Thread{
                Thread.sleep(3000)
                Repository.api.getNewsList().enqueue(this)
            }.start()
        }
    }

    override fun onResponse(
        call: Call<NewsListResponse>,
        response: Response<NewsListResponse>
    ) {
        Thread{
            val list = response.body()?.payload
                ?.filter { x -> x.publicationDate.milliseconds >= time }
                ?.map { r -> NewsItem(r.id, r.name, r.text, r.publicationDate.milliseconds, Repository.isFavourite(r.id), "no content") }
            list?.forEach { Repository.insert(it)}
        }.start()
        swiperReference?.get()?.isRefreshing = false
    }
}