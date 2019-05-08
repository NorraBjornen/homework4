package com.example.myapplication.controller.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.*
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.controller.recyclerview.holders.DateHolder
import com.example.myapplication.controller.recyclerview.holders.NewsItemHolder
import kotlin.collections.HashMap

class NewsDisplayAdapter(private val onClickListener: OnRecyclerItemClickCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
    }

    private var headersIds : List<Int> = ArrayList()
    private var map : HashMap<Int, NewsItem> = HashMap()
    private var newsList : List<NewsItem> = ArrayList()


    override fun getItemViewType(position: Int): Int = if (headersIds.contains(position)) TYPE_HEADER else TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_ITEM -> NewsItemHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.news_item,
                    parent,
                    false
                ),
                onClickListener
            )
            else -> DateHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.date_item,
                    parent,
                    false
                )
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

    fun setData(newsList : List<NewsItem>, headersIds : List<Int>, map : HashMap<Int, NewsItem>){
        this.newsList = newsList
        this.headersIds = headersIds
        this.map = map
        notifyDataSetChanged()
    }
}