package com.example.myapplication.async_tasks

import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.myapplication.MyItemDecoration
import com.example.myapplication.PageFragment
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.adapters.MyAdapter
import java.lang.ref.WeakReference

/*
class AdapterInitAsyncTask(private val fragment : PageFragment, private val recyclerView: WeakReference<RecyclerView>) : AsyncTask<Int, Unit, MyAdapter>() {
    override fun doInBackground(vararg params: Int?): MyAdapter {
        val adapter = MyAdapter(params[0]!!, WeakReference(fragment.activity as MainActivity))
        adapter.fillData()
        return adapter
    }

    override fun onPostExecute(myAdapter: MyAdapter) {
        recyclerView.get()?.layoutManager = LinearLayoutManager(fragment.activity)
        recyclerView.get()?.adapter = myAdapter
        recyclerView.get()?.addItemDecoration(MyItemDecoration(fragment.activity as Context))
        fragment.adapter = myAdapter
    }
}*/
