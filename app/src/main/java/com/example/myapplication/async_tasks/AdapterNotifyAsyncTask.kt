package com.example.myapplication.async_tasks

import android.os.AsyncTask
import com.example.myapplication.Repository
import com.example.myapplication.adapters.MyAdapter

class AdapterNotifyAsyncTask(private val adapter: MyAdapter) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit){
        adapter.fillData()
    }

    override fun onPostExecute(unit: Unit) {
        adapter.notifyDataSetChanged()
        Repository.hasChanges = false
    }
}