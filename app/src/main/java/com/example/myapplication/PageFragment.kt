package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.adapters.MyAdapter
import com.example.myapplication.async_tasks.AdapterInitAsyncTask
import com.example.myapplication.async_tasks.AdapterNotifyAsyncTask
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
    lateinit var adapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments != null)
            currentTabNumber = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val recyclerView = view as RecyclerView
        AdapterInitAsyncTask(this, WeakReference(recyclerView)).execute(currentTabNumber)
        return view
    }

    override fun onResume() {
        super.onResume()
        if(currentTabNumber == 2 && Repository.hasChanges)
            AdapterNotifyAsyncTask(WeakReference(adapter)).execute()
    }
}