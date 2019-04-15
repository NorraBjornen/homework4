package com.example.myapplication.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsTable")
    fun getAllNews(): Single<List<NewsItem>>

    @Query("SELECT * FROM newsTable WHERE fav=1")
    fun getFavouriteNews(): Single<List<NewsItem>>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun getNewsItemById(idToSelect: Int): Single<NewsItem>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun gettNewsItemById(idToSelect: Int): NewsItem

    @Query("SELECT Count(id) FROM favouritesTable WHERE id=:idToSelect")
    fun isFavourite(idToSelect: Int): Int

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Query("UPDATE newsTable SET fav=1 WHERE id=:id")
    fun addToFavourite(id: Int)

    @Query("UPDATE newsTable SET fav=0 WHERE id=:id")
    fun deleteFavourite(id: Int)

}