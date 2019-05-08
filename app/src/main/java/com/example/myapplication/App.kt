package com.example.myapplication

import android.app.Application
import android.arch.persistence.room.Room
import com.example.myapplication.model.Repository
import com.example.myapplication.model.database.NewsDao
import com.example.myapplication.model.database.NewsDatabase
import com.example.myapplication.model.getDaysAgo
import com.example.myapplication.model.network.WebService
import io.reactivex.schedulers.Schedulers
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class App : Application(){
    companion object{
        val daysAgoToLoad = getDaysAgo(600).time
        val daysAgoToStore = getDaysAgo(60).time
        lateinit var dataBase : NewsDatabase
        lateinit var api : WebService
        lateinit var newsDao : NewsDao
    }

    override fun onCreate() {
        super.onCreate()
        initDatabase()
        initApi()

        Repository.deleteOld(daysAgoToStore)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun initDatabase(){
        dataBase = Room.databaseBuilder(
            applicationContext.applicationContext,
            NewsDatabase::class.java,
            "myApp.db")
            .fallbackToDestructiveMigration()
            .build()
        newsDao = dataBase.newsDao()
    }

    private fun initApi(){
        api = Retrofit.Builder()
            .baseUrl(resources.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WebService::class.java)
    }
}


