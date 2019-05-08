package com.example.myapplication.controller.pager

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.myapplication.*
import com.example.myapplication.controller.recyclerview.MyItemDecoration
import com.example.myapplication.controller.recyclerview.NewsDisplayAdapter
import com.example.myapplication.controller.recyclerview.OnRecyclerItemClickCallback
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.toastLong

class PageFragment : MyMvpFragment(), NewsView{
    @InjectPresenter
    lateinit var presenter : NewsPresenter

    private var currentTabNumber: Int = 0
    private lateinit var adapter : NewsDisplayAdapter
    private lateinit var swiper : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        swiper = view.findViewById(R.id.swiper)

        adapter = NewsDisplayAdapter(activity as OnRecyclerItemClickCallback)

        presenter.subscribeLocal(currentTabNumber)

        recyclerView.layoutManager = LinearLayoutManager(activity as Context)
        recyclerView.addItemDecoration(MyItemDecoration(activity as Context))
        recyclerView.adapter = adapter

        swiper.setOnRefreshListener {
            swiper.isRefreshing = true
            presenter.subscribeApi()
        }

        return view
    }

    override fun setData(newsList: List<NewsItem>, headersIds: List<Int>, map: HashMap<Int, NewsItem>) {
        adapter.setData(newsList, headersIds, map)
    }

    override fun stopRotating() {
        swiper.isRefreshing = false
    }

    override fun showNetworkError() {
        activity?.toastLong(resources.getString(R.string.network_error_message))
    }

    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        const val TAB_NUMBER_RECENT = 1
        const val TAB_NUMBER_FAVOURITE = 2

        fun newInstance(pageNumber: Int): PageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, pageNumber)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}