package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.NewsItem
import com.example.myapplication.R

class NewsViewerActivity : AppCompatActivity() {
    companion object {
        private const val ARG_NEWS_ID = "news_id"

        fun newIntent(context: Context, id : Int) : Intent {
            val intent = Intent(context, NewsViewerActivity::class.java)
            intent.putExtra(ARG_NEWS_ID, id)
            return intent
        }
    }

    private lateinit var menu: Menu
    private lateinit var newsItem : NewsItem

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu, menu)
        if(NewsItem.favouriteIds.contains(newsItem.id))
            changeIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item_fav -> {
                val id = newsItem.id
                if(NewsItem.favouriteIds.contains(id))
                    Toast.makeText(this, resources.getString(R.string.newsItem_already), Toast.LENGTH_SHORT).show()
                else{
                    NewsItem.favouriteIds.add(id)
                    NewsItem.favouritesChanged = true
                    Toast.makeText(this, resources.getString(R.string.newsItem_added), Toast.LENGTH_SHORT).show()
                    changeIcon()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeIcon(){
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav_added)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_viewer)

        val id = intent.getIntExtra(ARG_NEWS_ID, 0)

        newsItem = NewsItem.news[id]

        title = newsItem.name

        val content = findViewById<TextView>(R.id.news_content)
        content.text = newsItem.content

        val date = findViewById<TextView>(R.id.news_date)
        date.text = newsItem.date
    }
}
