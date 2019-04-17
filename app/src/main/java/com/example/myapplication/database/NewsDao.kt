package com.example.myapplication.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsTable")
    fun getAllNews(): Flowable<List<NewsItem>>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun getNewsItemById(idToSelect: Int): Single<NewsItem>

    @Insert(onConflict = IGNORE)
    fun insert(newsItem: NewsItem)

    @Query("UPDATE newsTable SET fav=1 WHERE id=:id")
    fun addToFavourite(id: Int)

    @Query("UPDATE newsTable SET fav=0 WHERE id=:id")
    fun deleteFavourite(id: Int)
}