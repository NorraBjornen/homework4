package com.example.myapplication.async_tasks

import android.os.AsyncTask
import com.example.myapplication.Repository
import com.example.myapplication.adapters.MyAdapter
import java.lang.ref.WeakReference

class AdapterNotifyAsyncTask(private val adapter: WeakReference<MyAdapter>) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit){
        adapter.get()?.fillData()
    }

    override fun onPostExecute(unit: Unit) {
        adapter.get()?.notifyDataSetChanged()
        Repository.hasChanges = false
    }
}