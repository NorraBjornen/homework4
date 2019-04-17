package com.example.myapplication.model

import com.example.myapplication.model.database.NewsDao
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.network.WebService
import io.reactivex.Flowable
import io.reactivex.Single

class Repository(private val newsDao: NewsDao){
    companion object {
        private lateinit var repository: Repository
        lateinit var api : WebService

        fun set(newsDao: NewsDao, api : WebService) {
            repository =
                Repository(newsDao)
            Companion.api = api
        }

        fun getAllNews() : Flowable<List<NewsItem>>{
            return repository.newsDao.getAllNews()
        }

        fun getNewsItemById(id: Int) : Single<NewsItem>{
            return repository.newsDao.getNewsItemById(id)
        }

        fun insert(newsItem: NewsItem){
            repository.newsDao.insert(newsItem)
        }

        fun updateContent(id: Int, content: String){
            repository.newsDao.updateContent(id, content)
        }

        fun addToFavourite(newsItem: NewsItem){
            newsItem.isFav = 1
            Thread{ repository.newsDao.addToFavourite(newsItem.id)}.start()
        }

        fun deleteFavourite(newsItem: NewsItem){
            newsItem.isFav = 0
            Thread{ repository.newsDao.deleteFavourite(newsItem.id)}.start()
        }

        fun isFavourite(id : Int) : Int{
            return repository.newsDao.isFavourite(id)
        }

        fun deleteOld(time : Long){
            Thread{ repository.newsDao.deleteOld(time)}.start()
        }
    }
}