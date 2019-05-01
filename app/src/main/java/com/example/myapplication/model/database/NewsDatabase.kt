package com.example.myapplication.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [NewsItem::class], version = 8)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao() : NewsDao
}