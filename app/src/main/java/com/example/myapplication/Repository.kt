package com.example.myapplication

import com.example.myapplication.database.NewsDao

class Repository(private val newsDao: NewsDao){
    companion object {
        private lateinit var repository: Repository

        fun dao() : NewsDao{
            return repository.newsDao
        }

        fun set(newsDao: NewsDao) {
            repository = Repository(newsDao)
        }

        fun addToFavourite(id : Int){
            Thread{ repository.newsDao.addToFavourite(id)}.start()
        }

        fun deleteFavourite(id : Int){
            Thread{ repository.newsDao.deleteFavourite(id)}.start()
        }
    }
}