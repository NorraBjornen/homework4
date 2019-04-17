package com.example.myapplication.model.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsTable")
    fun getAllNews(): Flowable<List<NewsItem>>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun getNewsItemById(idToSelect: Int): Single<NewsItem>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Query("UPDATE newsTable SET fav=1 WHERE id=:id")
    fun addToFavourite(id: Int)

    @Query("UPDATE newsTable SET content=:content WHERE id=:id")
    fun updateContent(id: Int, content: String)

    @Query("UPDATE newsTable SET fav=0 WHERE id=:id")
    fun deleteFavourite(id: Int)

    @Query("SELECT Count(id) FROM newsTable WHERE fav=1 AND id=:id")
    fun isFavourite(id: Int) : Int

    @Query("DELETE FROM newsTable WHERE date<:time")
    fun deleteOld(time : Long)
}