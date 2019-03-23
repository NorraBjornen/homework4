package com.example.myapplication

import com.example.myapplication.database.Favourite
import com.example.myapplication.database.NewsDao
import com.example.myapplication.database.NewsItem

class Repository(private val newsDao: NewsDao){
    companion object {
        private lateinit var repository: Repository

        fun set(newsDao: NewsDao) {
            repository = Repository(newsDao)
        }

        fun getAllNews(): List<NewsItem> {
            return repository.newsDao.getAllNews()
        }

        fun getFavouriteNews(): List<NewsItem> {
            val favourites = ArrayList<NewsItem>()
            repository.newsDao.getFavouriteNewsIds().forEach {
                favourites.add(repository.newsDao.getNewsItemById(it))
            }
            return favourites
        }

        fun isFavourite(id: Int): Boolean {
            return repository.newsDao.isFavourite(id) == 1
        }

        fun addToFavourites(id: Int) {
            repository.newsDao.addToFavourite(Favourite(id))
            hasChanges = true
        }

        fun getNewsItemById(id: Int): NewsItem {
            return repository.newsDao.getNewsItemById(id)
        }

        fun deleteFavourite(id : Int){
            repository.newsDao.deleteFavourite(id)
            hasChanges = true
        }

        var hasChanges = false
    }
}