package com.example.myapplication.model.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsTable ORDER BY date")
    fun getAllNews(): Flowable<List<NewsItem>>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun getNewsItemById(idToSelect: Int): Maybe<NewsItem>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Insert(onConflict = REPLACE)
    fun insertAll(newsItems: List<NewsItem>)

    @Query("UPDATE newsTable SET fav=1 WHERE id=:id")
    fun addToFavourite(id: Int)

    @Query("UPDATE newsTable SET content=:content WHERE id=:id")
    fun updateContent(id: Int, content: String)

    @Query("UPDATE newsTable SET fav=0 WHERE id=:id")
    fun deleteFavourite(id: Int)

    @Query("SELECT Count(id) FROM newsTable WHERE fav=1 AND id=:id")
    fun isFavourite(id: Int) : Boolean

    @Query("DELETE FROM newsTable WHERE date<:time AND fav=0")
    fun deleteOld(time : Long)
}