package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast


class NewsViewer : AppCompatActivity() {
    companion object {
        private const val ARG_NEWS_ID = "news_id"

        fun newIntent(context: Context, id : Int) : Intent {
            val intent = Intent(context, NewsViewer::class.java)
            intent.putExtra(ARG_NEWS_ID, id)
            return intent
        }
    }

    private lateinit var news : News

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item_fav -> {
                val id = news.id
                if(News.favouriteIds.contains(id))
                    Toast.makeText(this, "Уже в избранном", Toast.LENGTH_SHORT).show()
                else{
                    News.favouriteIds.add(id)
                    Toast.makeText(this, "Новость добавлена в избранное", Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_viewer)

        val id = intent.getIntExtra(ARG_NEWS_ID, 0)
        news = News.news[id]

        title = news.name

        val content = findViewById<TextView>(R.id.news_content)
        content.text = news.content

        val date = findViewById<TextView>(R.id.news_date)
        date.text = news.date
    }
}
