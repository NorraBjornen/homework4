package com.example.myapplication.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [NewsItem::class, Favourite::class], version = 2)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao() : NewsDao
}