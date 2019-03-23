package com.example.myapplication.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsTable")
    fun getAllNews(): List<NewsItem>

    @Query("SELECT id FROM favouritesTable")
    fun getFavouriteNewsIds(): List<Int>

    @Query("SELECT * FROM newsTable WHERE id=:idToSelect")
    fun getNewsItemById(idToSelect: Int): NewsItem

    @Query("SELECT Count(id) FROM favouritesTable WHERE id=:idToSelect")
    fun isFavourite(idToSelect: Int): Int

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Insert(onConflict = REPLACE)
    fun addToFavourite(favourite: Favourite)

    @Query("DELETE FROM favouritesTable WHERE id=:idToDelete")
    fun deleteFavourite(idToDelete: Int)
}