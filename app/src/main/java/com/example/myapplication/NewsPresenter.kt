package com.example.myapplication

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
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

    fun dispose(){
        disposable.dispose()
    }

    fun fillData(currentTabNumber : Int){
        subscribeLocal(currentTabNumber)
        subscribeApi(currentTabNumber)
    }

    private fun subscribeLocal(currentTabNumber: Int){
        val source = Repository.getAllNewsLocal()
        subscribe(source, currentTabNumber, false)
    }

    private fun subscribeApi(currentTabNumber: Int){
        val source = Repository.getAllNewsFromApi()
        subscribe(source, currentTabNumber, true)
    }

    private fun subscribe(source : Flowable<List<NewsItem>>, currentTabNumber: Int, isApi : Boolean){
        when (currentTabNumber) {
            2 -> {
                disposable.add(
                    source
                        .subscribeOn(Schedulers.io())
                        .flatMap{ newsList ->
                            Flowable.fromIterable(newsList)
                                .filter{ newsItem -> Repository.isFavourite(newsItem.id) == 1 }
                                .toList()
                                .toFlowable()
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ l ->
                            if(!isApi){
                                newsList = l
                                set()
                            } else
                                viewState.stopRotating()
                        }, {

                        }))
            }
            else -> {
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
                        .subscribe({ l ->
                            if(isApi){
                                viewState.stopRotating()
                                Thread{l.forEach{Repository.insert(it)}}.start()
                            }
                            else{
                                newsList = l
                                set()
                            }
                        }, {
                            if(isApi){
                                viewState.stopRotating()
                                viewState.showNetworkError()
                            }
                        }))
            }
        }
    }

    private fun set(){
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
}