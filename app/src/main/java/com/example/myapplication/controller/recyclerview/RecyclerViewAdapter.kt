package com.example.myapplication.controller.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.*
import com.example.myapplication.activities.NewsListViewerActivity
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.controller.recyclerview.holders.DateHolder
import com.example.myapplication.controller.recyclerview.holders.NewsItemHolder
import com.example.myapplication.model.Repository
import com.example.myapplication.model.compareNewsItemsByDate
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import io.reactivex.disposables.CompositeDisposable

class RecyclerViewAdapter(private val currentTabNumber : Int,
                          private val activity: WeakReference<NewsListViewerActivity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
    }

    private val headersIds : ArrayList<Int> = ArrayList()
    private val map : HashMap<Int, NewsItem> = HashMap()
    private var newsList : List<NewsItem> = emptyList()

    private val disposable = CompositeDisposable()

    override fun getItemViewType(position: Int): Int = if (headersIds.contains(position)) TYPE_HEADER else TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_ITEM -> NewsItemHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.news_item,
                    parent,
                    false
                ),
                activity.get() as OnRecyclerItemClickCallback
            )
            else -> DateHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.date_item,
                    parent,
                    false
                ), activity.get() as Context
            )
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            TYPE_ITEM -> (viewHolder as NewsItemHolder).bind(map[position]!!)
            TYPE_HEADER -> (viewHolder as DateHolder).bind(map[position]!!.date)
        }
    }

    override fun getItemCount(): Int = newsList.size + headersIds.size

    fun fillData(){
        when (currentTabNumber) {
            2 -> {
                disposable.add(
                    Repository.getAllNews()
                    .subscribeOn(Schedulers.io())
                    .flatMap{ newsList ->
                        Flowable.fromIterable(newsList)
                            .filter{ newsItem -> Repository.isFavourite(newsItem.id) == 1 }
                            .sorted{x, y -> compareNewsItemsByDate(x, y) }
                            .toList()
                            .toFlowable()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { l ->
                        newsList = l
                        set()
                    })
            }
            else -> {
                disposable.add(
                    Repository.getAllNews()
                    .subscribeOn(Schedulers.io())
                    .flatMap{ newsList ->
                        Flowable.fromIterable(newsList)
                            .sorted{x, y -> compareNewsItemsByDate(x, y) }
                            .toList()
                            .toFlowable()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{ l ->
                        newsList = l
                        set()
                    })
            }
        }
    }

    private fun set(){
        var headersCount = 0
        var prevDate : Long? = null
        var i = 0
        headersIds.clear()
        map.clear()

        newsList.forEach {
            val itemDate = it.date
            if(prevDate == null || prevDate != itemDate) {
                prevDate = itemDate
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

        notifyDataSetChanged()
    }

    fun dispose(){
        disposable.dispose()
    }
}