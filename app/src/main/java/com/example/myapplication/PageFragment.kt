package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.news_item.view.*


class PageFragment : Fragment() {
    companion object {
        const val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): PageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mPage: Int = 0
    private val newsList = ArrayList<News>()
    private var adapter : Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            mPage = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_page, container, false)
            val recyclerView = view as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = Adapter(activity as Context, newsList)
            recyclerView.adapter = adapter
            return view
    }

    override fun onResume() {
        super.onResume()
        newsList.clear()
        when(mPage){
            1 -> News.recentIds.forEach {
                newsList.add(News.news[it])
            }
            2 -> News.favouriteIds.forEach {
                newsList.add(News.news[it])
            }
            else -> newsList.addAll(News.news)
        }
        adapter?.notifyDataSetChanged()
    }

    class Holder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val name = itemView.news_item_name!!
        private val summary = itemView.news_item_summary!!
        private val date = itemView.news_item_date!!
        private lateinit var news: News

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(news: News){
            this.news = news
            name.text = news.name
            summary.text = news.summary
            date.text = news.date
        }

        override fun onClick(v: View?) {
            context.startActivity(NewsViewer.newIntent(context, news.id))
        }
    }

    class Adapter(private val context : Context, private val content : ArrayList<News>) : RecyclerView.Adapter<Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent,false), context)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(content[position])
        }

        override fun getItemCount(): Int {
            return content.size
        }
    }
}