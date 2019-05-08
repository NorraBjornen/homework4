package com.example.myapplication.model

import com.example.myapplication.App
import com.example.myapplication.model.database.NewsItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

object Repository{
    fun getAllNewsFromApi() : Flowable<List<NewsItem>>{
        return App.api.getNewsList()
                .flatMap{ newsList ->
                    Flowable.fromIterable(newsList.payload)
                        .filter { x -> x.publicationDate.milliseconds >= App.daysAgoToLoad }
                        .map {
                            r -> NewsItem(r.id, r.text, r.publicationDate.milliseconds, isFavourite(r.id), "no content")
                        }
                        .toList()
                }
                .toFlowable()
    }

    fun getAllNewsLocal() : Flowable<List<NewsItem>>{
        return App.newsDao.getAllNews()
    }

    fun getNewsItemById(id: Int) : Maybe<NewsItem>{
        return App.newsDao.getNewsItemById(id)
    }

    fun insertAll(newsItems: List<NewsItem>){
        App.newsDao.insertAll(newsItems)
    }

    fun updateContent(id: Int, content: String): Completable{
        return Completable.fromRunnable{
            App.newsDao.updateContent(id, content)
        }
    }

    fun addToFavourite(newsItem: NewsItem): Completable{
        return Completable.fromRunnable {
            newsItem.isFav = true
            App.newsDao.addToFavourite(newsItem.id)
        }
    }

    fun deleteFavourite(newsItem: NewsItem): Completable{
        return Completable.fromRunnable {
            newsItem.isFav = false
            App.newsDao.deleteFavourite(newsItem.id)
        }
    }

    fun isFavourite(id : Int) : Boolean{
        return App.newsDao.isFavourite(id)
    }

    fun deleteOld(time : Long): Completable{
        return Completable.fromRunnable {
            App.newsDao.deleteOld(time)
        }
    }
}