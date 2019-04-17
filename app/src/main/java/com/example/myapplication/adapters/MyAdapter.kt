package com.example.myapplication.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.*
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.database.NewsItem
import com.example.myapplication.holders.DateHolder
import com.example.myapplication.holders.NewsItemHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class MyAdapter(private val currentTabNumber : Int,
                private val activity: WeakReference<MainActivity>,
                private val recyclerViewReference: WeakReference<RecyclerView>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val headersIds : ArrayList<Int> = ArrayList()

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
        val usualFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
    }

    private val map : HashMap<Int, NewsItem> = HashMap()
    private lateinit var newsList : List<NewsItem>

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
                disposable.add(Repository.dao().getAllNews()
                    .subscribeOn(Schedulers.io())
                    .flatMap{ newsList ->
                        Flowable.fromIterable(newsList).filter { newsItem -> newsItem.isFav == 1 }.toList().toFlowable()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { l ->
                        newsList = l
                        set()
                        Repository.hasChanges = false
                    })
            }
            else -> {
                disposable.add(Repository.dao().getAllNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{ l ->
                        newsList = l
                        set()
                    })
            }
        }
    }

    fun set(){
        var headersCount = 0
        var prevDate : String? = null
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

        val recyclerView = recyclerViewReference.get()
        recyclerView?.layoutManager = LinearLayoutManager(activity.get())
        recyclerView?.adapter = this@MyAdapter
        recyclerView?.addItemDecoration(MyItemDecoration(activity.get() as Context))
    }

    fun dispose(){
        disposable.dispose()
    }
}