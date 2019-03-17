package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.adapters.Adapter
import kotlin.collections.ArrayList

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

    private var currentTabNumber: Int = 0
    private lateinit var adapter : Adapter
    private val headersIds : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_page, container, false)
            val recyclerView = view as RecyclerView

            recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = Adapter(headersIds, currentTabNumber, activity as MainActivity)

            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(MyItemDecoration(activity!!))
            return view
    }

    override fun onResume() {
        super.onResume()
        if(NewsItem.favouritesChanged && currentTabNumber == 2)
            adapter.update()
    }
}