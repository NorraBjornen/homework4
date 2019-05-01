package com.example.myapplication

import com.arellomobile.mvp.MvpView
import com.example.myapplication.model.database.NewsItem

interface NewsView : MvpView{
    fun setData(newsList : List<NewsItem>, headersIds : List<Int>, map : HashMap<Int, NewsItem>)
    fun stopRotating()
    fun showNetworkError()
}