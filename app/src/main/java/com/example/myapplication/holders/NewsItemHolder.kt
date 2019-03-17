package com.example.myapplication.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.NewsItem
import com.example.myapplication.OnRecyclerItemClickCallback
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItemHolder(itemView: View, private val callback: OnRecyclerItemClickCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val name = itemView.news_item_name!!
    private val summary = itemView.news_item_summary!!
    private val date = itemView.news_item_date!!
    private lateinit var newsItem: NewsItem

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(newsItem: NewsItem){
        this.newsItem = newsItem
        name.text = newsItem.name
        summary.text = newsItem.summary
        date.text = newsItem.date
    }

    override fun onClick(v: View?) =  callback.click(newsItem.id)
}