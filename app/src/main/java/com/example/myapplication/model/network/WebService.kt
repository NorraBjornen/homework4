package com.example.myapplication.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("v1/news")
    fun getNewsList(): Call<NewsListResponse>

    @GET("v1/news_content")
    fun getNewsItem(@Query("id") id : Int): Call<NewsItemResponse>
}