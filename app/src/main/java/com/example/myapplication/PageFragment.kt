package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.date_item.view.*
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PageFragment : Fragment() {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
        const val ARG_PAGE = "ARG_PAGE"
        val usualFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))

        fun newInstance(page: Int): PageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private enum class DAYS {
        TODAY,
        YESTERDAY,
        OTHER
    }

    lateinit var callback : OnRecyclerItemClickCallback
    private var currentTabNumber: Int = 0
    private var newsList : List<NewsItem> = emptyList()
    private lateinit var adapter : Adapter
    val map : HashMap<Int, NewsItem> = HashMap()
    val headersIds : ArrayList<Int> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fillData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = activity as OnRecyclerItemClickCallback
        if (arguments != null)
            currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_page, container, false)
            val recyclerView = view as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = Adapter()
            recyclerView.adapter = adapter
            val decoration = MyItemDecoration(activity!!)
            recyclerView.addItemDecoration(decoration)
            return view
    }

    override fun onResume() {
        super.onResume()
        if(NewsItem.favouritesChanged && currentTabNumber == 2)
            adapter.update()
    }

    fun fillData(){
        newsList = when (currentTabNumber) {
            1 -> NewsItem.recentIds.map { NewsItem.news[it] }
            2 -> NewsItem.favouriteIds.map { NewsItem.news[it] }
            else -> NewsItem.news.toList()
        }.sortedWith(compareByDescending{ usualFormat.parse(it.date) })

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
    }

    inner class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val txt = itemView.date_txt!!

        fun bind(dateText: String){
            val c = Calendar.getInstance()
            c.time = usualFormat.parse(dateText)

            txt.text = when(whichDay(c)){
                DAYS.TODAY -> resources.getString(R.string.today)
                DAYS.YESTERDAY -> resources.getString(R.string.yesterday)
                DAYS.OTHER -> "${c.get(Calendar.DAY_OF_MONTH)} ${getMonthForInt(c.get(Calendar.MONTH))}, ${c.get(Calendar.YEAR)}"
            }
        }

        private fun getMonthForInt(num: Int): String {
            var month = "wrong number"
            val months = DateFormatSymbols().months
            if (num in 0 until 11)
                month = months[num]
            return month
        }

        private fun whichDay(c : Calendar) : DAYS {
            val today = Calendar.getInstance()
            today.add(Calendar.DAY_OF_YEAR, 0)
            if(today.get(Calendar.YEAR) == c.get(Calendar.YEAR)
                && today.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR))
                return DAYS.TODAY
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_YEAR, -1)
            if (yesterday.get(Calendar.YEAR) == c.get(Calendar.YEAR)
                && yesterday.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR))
                return DAYS.YESTERDAY
            return DAYS.OTHER
        }
    }

    inner class NewsItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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

    inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemViewType(position: Int): Int = if (headersIds.contains(position)) TYPE_HEADER else TYPE_ITEM

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when(viewType){
                TYPE_ITEM -> NewsItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent,false))
                else -> DateHolder(LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent,false))
            }
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            when(viewHolder.itemViewType){
                TYPE_ITEM -> (viewHolder as NewsItemHolder).bind(map[position]!!)
                TYPE_HEADER -> (viewHolder as DateHolder).bind(map[position]!!.date)
            }
        }

        override fun getItemCount(): Int = newsList.size + headersIds.size

        fun update(){
            fillData()
            notifyDataSetChanged()
        }
    }
}