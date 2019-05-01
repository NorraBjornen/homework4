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
import com.example.myapplication.activities.NewsListViewerActivity
import com.example.myapplication.controller.recyclerview.MyItemDecoration
import com.example.myapplication.controller.recyclerview.RecyclerViewAdapter
import com.example.myapplication.model.database.NewsItem
import com.example.myapplication.model.toastLong
import com.example.myapplication.model.toastShort
//import com.example.myapplication.model.network.ResponseCallback
import java.lang.ref.WeakReference

class PageFragment : MyMvpFragment(), NewsView{
    @InjectPresenter
    lateinit var presenter : NewsPresenter

    private var currentTabNumber: Int = 0
    private var adapter : RecyclerViewAdapter? = null
    private var swiper : SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments != null)
            currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        swiper = view.findViewById(R.id.swiper)

        if(adapter == null)
            adapter = RecyclerViewAdapter(WeakReference(activity as NewsListViewerActivity))

        presenter.fillData(currentTabNumber)

        recyclerView.layoutManager = LinearLayoutManager(activity as Context)
        recyclerView.addItemDecoration(MyItemDecoration(activity as Context))
        recyclerView.adapter = adapter

        swiper?.setOnRefreshListener {
            swiper?.isRefreshing = true
            presenter.fillData(currentTabNumber)
        }

        return view
    }

    override fun setData(newsList: List<NewsItem>, headersIds: List<Int>, map: HashMap<Int, NewsItem>) {
        adapter?.setData(newsList, headersIds, map)
    }

    override fun stopRotating() {
        swiper?.isRefreshing = false
    }

    override fun showNetworkError() {
        activity?.toastLong(resources.getString(R.string.network_error_message))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dispose()
    }

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
}