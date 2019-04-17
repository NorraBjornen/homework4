package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.adapters.MyAdapter
import java.lang.ref.WeakReference

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
    private var adapter : MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments != null)
            currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val recyclerView = view as RecyclerView

        adapter?.dispose()
        //Делаю dispose именно здесь, так как retainInstance = true,
        //и вызов onCreateView является показателем того, что активити была пересоздана (кроме первого раза)

        adapter = MyAdapter(currentTabNumber, WeakReference(activity as MainActivity))
        adapter?.fillData()

        recyclerView.layoutManager = LinearLayoutManager(activity as Context)
        recyclerView.addItemDecoration(MyItemDecoration(activity as Context))
        recyclerView.adapter = adapter
        return view
    }
}