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

        private fun isFavourite(id: Int): Boolean {
            return repository.newsDao.isFavourite(id) == 1
        }

        fun addToFavourites(newsItem: NewsItem) {
            repository.newsDao.addToFavourite(Favourite(newsItem.id))
            newsItem.isFav = true
            hasChanges = true
        }

        fun getNewsItemById(id: Int): NewsItem {
            val newsItem = repository.newsDao.getNewsItemById(id)
            newsItem.isFav = isFavourite(id)
            return newsItem
        }

        fun deleteFavourite(newsItem: NewsItem){
            repository.newsDao.deleteFavourite(newsItem.id)
            newsItem.isFav = false
            hasChanges = true
        }

        var hasChanges = false
    }
}