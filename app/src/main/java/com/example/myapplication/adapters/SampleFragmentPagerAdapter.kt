package com.example.myapplication.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.myapplication.PageFragment

class SampleFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        const val PAGE_COUNT = 2
    }

    private val tabTitles = arrayOf("Последние", "Избранные")

    override fun getCount(): Int = PAGE_COUNT

    override fun getItem(position: Int): Fragment = PageFragment.newInstance(position + 1)

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]
}