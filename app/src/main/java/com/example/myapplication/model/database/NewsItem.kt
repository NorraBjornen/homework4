package com.example.myapplication.model.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "newsTable")
data class NewsItem (@PrimaryKey(autoGenerate = true) var id: Int,
                     @ColumnInfo(name = "name") var name: String,
                     @ColumnInfo(name = "text") var text : String,
                     @ColumnInfo(name = "date") var date : Long,
                     @ColumnInfo(name = "fav") var isFav: Int,
                     @ColumnInfo(name = "content") var content: String
)