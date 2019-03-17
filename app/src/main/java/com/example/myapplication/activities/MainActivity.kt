package com.example.myapplication.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.example.myapplication.OnRecyclerItemClickCallback
import com.example.myapplication.R
import com.example.myapplication.adapters.SampleFragmentPagerAdapter

class MainActivity : AppCompatActivity(), OnRecyclerItemClickCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = SampleFragmentPagerAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.sliding_tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun click(id : Int) {
        startActivity(NewsViewerActivity.newIntent(this, id))
    }
}
