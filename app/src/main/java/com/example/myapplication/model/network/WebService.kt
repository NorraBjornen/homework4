package com.example.myapplication.model.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("v1/news")
    fun getNewsList(): Single<TinkoffApiResponse<List<NewsTitle>>>

    @GET("v1/news_content")
    fun getNewsItem(@Query("id") id: Int): Single<TinkoffApiResponse<NewsItemDetails>>
}