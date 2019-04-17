package com.example.myapplication.controller.recyclerview.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.controller.recyclerview.OnRecyclerItemClickCallback
import com.example.myapplication.model.getTextDateFromMilliseconds
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItemHolder(itemView: View, private val callback: OnRecyclerItemClickCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val name = itemView.news_item_name!!
    private val date = itemView.news_item_date!!
    private var id : Int? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(newsItem: NewsItem){
        id = newsItem.id
        name.text = newsItem.text
        date.text = getTextDateFromMilliseconds(newsItem.date)
    }

    override fun onClick(v: View?) =  callback.click(id!!)
}