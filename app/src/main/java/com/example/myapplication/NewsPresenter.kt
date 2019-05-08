package com.example.myapplication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.myapplication.controller.pager.PageFragment
import com.example.myapplication.model.Repository
import com.example.myapplication.model.compareNewsItemsByDate
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.getTextDateFromMilliseconds
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class NewsPresenter : MvpPresenter<NewsView>(){
    private val disposable = CompositeDisposable()

    private var headersIds : ArrayList<Int> = ArrayList()
    private val map : HashMap<Int, NewsItem> = HashMap()
    private var newsList : List<NewsItem> = emptyList()

    fun subscribeLocal(currentTabNumber: Int){
        val source = Repository.getAllNewsLocal()
        disposable.add(
            source
                .subscribeOn(Schedulers.io())
                .flatMap{ newsList ->
                    var sortedNews = Flowable.fromIterable(newsList)
                        .sorted{x, y -> compareNewsItemsByDate(x, y) }

                    if(currentTabNumber == PageFragment.TAB_NUMBER_FAVOURITE)
                        sortedNews = sortedNews.filter{ newsItem -> Repository.isFavourite(newsItem.id) }

                    return@flatMap sortedNews.toList().toFlowable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ l ->
                    newsList = l
                    setDataWithDateHeaders()
                    viewState.stopRotating()
                }, {
                    viewState.stopRotating()
                }))
        subscribeApi()
    }

    fun subscribeApi(){
        val source = Repository.getAllNewsFromApi()
        disposable.add(
            source
                .subscribeOn(Schedulers.io())
                .flatMap{ newsList ->
                    Flowable.fromIterable(newsList)
                        .sorted{x, y -> compareNewsItemsByDate(x, y) }
                        .toList()
                        .toFlowable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    viewState.stopRotating()
                    viewState.showNetworkError()
                }
                .observeOn(Schedulers.io())
                .subscribe({ l ->
                    Repository.insertAll(l)
                    viewState.stopRotating()
                }, {

                }))
    }

    private fun setDataWithDateHeaders(){
        var headersCount = 0
        var d : String? = null
        var i = 0
        headersIds.clear()
        map.clear()

        newsList.forEach {
            val itemDate = getTextDateFromMilliseconds(it.date)
            if(d == null || d != itemDate) {
                d = itemDate
                val headerIndex = i + headersCount
                headersIds.add(headerIndex)
                map[headerIndex] = it
                headersCount++
            }
            i++
        }
        val fullSize = headersCount + newsList.size
        newsList.forEach {
            for(j in 0 until fullSize)
                if(!map.containsKey(j)){
                    map[j] = it
                    break
                }
        }
        viewState.setData(newsList, headersIds, map)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}