package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.*
import com.example.myapplication.model.network.NewsItemResponse
import com.example.myapplication.model.Repository
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.getTextDateFromMilliseconds
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class NewsItemViewerActivity: AppCompatActivity(), Callback<NewsItemResponse> {
    companion object {
        const val ARG_NEWS_ID = "news_id"

        fun newIntent(context: Context, id : Int) : Intent {
            val intent = Intent(context, NewsItemViewerActivity::class.java)
            intent.putExtra(ARG_NEWS_ID, id)
            return intent
        }
    }

    private val disposable = CompositeDisposable()
    private var id : Int? = null
    private lateinit var content : TextView
    private lateinit var date : TextView
    private lateinit var menu: Menu
    private lateinit var newsItem : NewsItem

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu, menu)
        menu.getItem(0).isEnabled = false
        menu.getItem(1).isEnabled = false
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_delete_disabled)
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav_disabled)
        title = resources.getString(R.string.loading)

        disposable.add(
            Repository.getNewsItemById(id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ newsItem ->
                this.newsItem = newsItem
                Repository.api.getNewsItem(id!!).enqueue(this)
                title = newsItem.text
                date.text = getTextDateFromMilliseconds(newsItem.date)
                if(newsItem.isFav == 1)
                    setIconsAdded()
                else
                    setIconsNotAdded()
            })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_item_fav -> {
                if(newsItem.isFav == 1)
                    Toast.makeText(this, resources.getString(R.string.newsItem_already), Toast.LENGTH_SHORT).show()
                else{
                    Repository.addToFavourite(newsItem)
                    Toast.makeText(this, resources.getString(R.string.newsItem_added), Toast.LENGTH_SHORT).show()
                    setIconsAdded()
                }
                return true
            }
            R.id.menu_item_del -> {
                Repository.deleteFavourite(newsItem)
                Toast.makeText(this, resources.getString(R.string.deleted), Toast.LENGTH_SHORT).show()
                setIconsNotAdded()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setIconsAdded(){
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_delete)
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav_added)
        menu.getItem(0).isEnabled = true
        menu.getItem(1).isEnabled = true
    }

    private fun setIconsNotAdded(){
        menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_delete_disabled)
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_action_fav)
        menu.getItem(0).isEnabled = false
        menu.getItem(1).isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_viewer)

        id = intent.getIntExtra(ARG_NEWS_ID, 0)

        content = findViewById(R.id.news_content)
        date = findViewById(R.id.news_date)

        content.text = resources.getString(R.string.loading)
    }


    override fun onFailure(call: Call<NewsItemResponse>, t: Throwable) {
        if (t !is UnknownHostException)
            throw t
        content.text = Html.fromHtml(newsItem.content, Html.FROM_HTML_MODE_COMPACT)
    }

    override fun onResponse(call: Call<NewsItemResponse>, response: Response<NewsItemResponse>) {
        val str = response.body()?.payload?.content
        content.text = Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT)
        newsItem.content = str!!
        Thread{ Repository.updateContent(id!!, str)}.start()
    }


    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}
