package com.example.myapplication.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "newsTable")
data class NewsItem (@PrimaryKey(autoGenerate = true) var id: Int,
                     @ColumnInfo(name = "name") var name: String,
                     @ColumnInfo(name = "summary") var summary : String,
                     @ColumnInfo(name = "content") var content: String,
                     @ColumnInfo(name = "date") var date: String
)

@Entity(tableName = "favouritesTable")
data class Favourite (@PrimaryKey(autoGenerate = false) var id: Int)