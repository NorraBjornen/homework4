package com.example.myapplication.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.example.myapplication.controller.recyclerview.OnRecyclerItemClickCallback
import com.example.myapplication.R
import com.example.myapplication.controller.pager.FragmentPagerAdapter

class NewsListViewerActivity : AppCompatActivity(),
    OnRecyclerItemClickCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = FragmentPagerAdapter(supportFragmentManager,
            resources.getString(R.string.recent),
            resources.getString(R.string.favourites)
        )

        val tabLayout = findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun click(id : Int) {
        startActivity(NewsItemViewerActivity.newIntent(this, id))
    }
}
