package com.example.myapplication

import com.example.myapplication.database.NewsDao
import com.example.myapplication.database.NewsItem

class Repository(private val newsDao: NewsDao){
    companion object {
        private lateinit var repository: Repository

        fun dao() : NewsDao{
            return repository.newsDao
        }

        fun set(newsDao: NewsDao) {
            repository = Repository(newsDao)
        }

        fun addToFavourites(newsItem: NewsItem) {
            repository.newsDao.addToFavourite(newsItem.id)
            newsItem.isFav = 1
            hasChanges = true
        }

        fun deleteFavourite(newsItem: NewsItem){
            repository.newsDao.deleteFavourite(newsItem.id)
            newsItem.isFav = 0
            hasChanges = true
        }

        var hasChanges = false
    }
}