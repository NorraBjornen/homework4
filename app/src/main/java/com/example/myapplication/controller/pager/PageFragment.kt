package com.example.myapplication.controller.pager

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.activities.NewsListViewerActivity
import com.example.myapplication.controller.recyclerview.MyItemDecoration
import com.example.myapplication.controller.recyclerview.RecyclerViewAdapter
import com.example.myapplication.model.Repository
import com.example.myapplication.model.network.ResponseCallback
import java.lang.ref.WeakReference

class PageFragment : Fragment(){
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

        swiper?.setOnRefreshListener {
            Repository.api.getNewsList().enqueue(
                ResponseCallback(
                    WeakReference(
                        swiper!!
                    )
                )
            )
        }

        adapter?.dispose()
        //Делаю dispose именно здесь, так как retainInstance = true,
        //и вызов onCreateView является показателем того, что активити была пересоздана (кроме первого раза)

        adapter = RecyclerViewAdapter(
            currentTabNumber,
            WeakReference(activity as NewsListViewerActivity)
        )
        adapter?.fillData()

        recyclerView.layoutManager = LinearLayoutManager(activity as Context)
        recyclerView.addItemDecoration(MyItemDecoration(activity as Context))
        recyclerView.adapter = adapter
        return view
    }
}