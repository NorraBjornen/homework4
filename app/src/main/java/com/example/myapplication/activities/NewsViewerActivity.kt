package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.myapplication.*
import com.example.myapplication.async_tasks.ItemGettingAsyncTask
import com.example.myapplication.database.NewsItem
import java.lang.ref.WeakReference

class NewsViewerActivity: AppCompatActivity() {
    companion object {
        const val ARG_NEWS_ID = "news_id"
        const val ADDED = 1
        const val REMOVED = -1

        fun newIntent(context: Context, id : Int) : Intent {
            val intent = Intent(context, NewsViewerActivity::class.java)
            intent.putExtra(ARG_NEWS_ID, id)
            return intent
        }
    }

    private var id : Int? = null
    private lateinit var content : TextView
    private lateinit var date : TextView
    private lateinit var handler: MyHandler
    private lateinit var menu: Menu
    lateinit var newsItem : NewsItem

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        ItemGettingAsyncTask(WeakReference(content), WeakReference(date)).execute(id)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item_fav -> {
                val id = newsItem.id
                Thread{
                    val msg = handler.obtainMessage()
                    if(Repository.isFavourite(id))
                        msg.obj = resources.getString(R.string.newsItem_already)
                    else{
                        Repository.addToFavourites(newsItem.id)
                        msg.obj = resources.getString(R.string.newsItem_added)
                        handler.sendEmptyMessage(ADDED)
                    }
                    handler.sendMessage(msg)
                }.start()
                return true
            }
            R.id.menu_item_del -> {
                Thread{
                    val msg = handler.obtainMessage()
                    msg.obj = resources.getString(R.string.deleted)
                    Repository.deleteFavourite(newsItem.id)
                    handler.sendEmptyMessage(REMOVED)
                    handler.sendMessage(msg)
                }.start()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setIconsAdded(){
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav_added)
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_delete)
        menu.getItem(0).isEnabled = true
    }

    fun setIconsNotAdded(){
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav)
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_delete_disabled)
        menu.getItem(0).isEnabled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_viewer)

        id = intent.getIntExtra(ARG_NEWS_ID, 0)
        content = findViewById(R.id.news_content)
        date = findViewById(R.id.news_date)

        handler = MyHandler(this)
    }
}
