package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

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

    class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        companion object {
            const val PAGE_COUNT = 2
        }

        private val tabTitles = arrayOf("Последние", "Избранные")

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return PageFragment.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }
}
