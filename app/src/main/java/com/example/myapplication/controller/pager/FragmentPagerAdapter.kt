package com.example.myapplication.controller.pager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        const val PAGE_COUNT = 2
    }

    private val tabTitles = arrayOf("Последние", "Избранные")

    override fun getCount(): Int = PAGE_COUNT

    override fun getItem(position: Int): Fragment = PageFragment.newInstance(position + 1)

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]
}