package com.example.myapplication

import android.app.Application
import android.arch.persistence.room.Room
import com.example.myapplication.model.Repository
import com.example.myapplication.model.network.ResponseCallback
import com.example.myapplication.model.database.NewsDatabase
import com.example.myapplication.model.getDaysAgo
import com.example.myapplication.model.network.WebService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class App : Application(){
    private val time = getDaysAgo(500).time

    override fun onCreate() {
        super.onCreate()

        val newsDao = Room.databaseBuilder(
            applicationContext.applicationContext,
            NewsDatabase::class.java,
            "myApp.db")
            .fallbackToDestructiveMigration()
            .build()
            .newsDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tinkoff.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WebService::class.java)

        Repository.set(newsDao, api)
        Repository.deleteOld(time)

        api.getNewsList().enqueue(ResponseCallback())
    }
}