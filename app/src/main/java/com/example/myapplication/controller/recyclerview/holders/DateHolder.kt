package com.example.myapplication.controller.recyclerview.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.*
import com.example.myapplication.model.DAYS
import com.example.myapplication.model.getMonthNameFromNumber
import com.example.myapplication.model.whichDay
import kotlinx.android.synthetic.main.date_item.view.*
import java.util.*

class DateHolder(itemView: View, private val context : Context) : RecyclerView.ViewHolder(itemView){
    private val txt = itemView.date_txt!!

    fun bind(milliseconds: Long){
        val c = Calendar.getInstance()
        c.time = Date(milliseconds)

        txt.text = when(whichDay(c)){
            DAYS.TODAY -> context.resources.getString(R.string.today)
            DAYS.YESTERDAY -> context.resources.getString(R.string.yesterday)
            DAYS.OTHER -> "${c.get(Calendar.DAY_OF_MONTH)} ${getMonthNameFromNumber(
                c.get(Calendar.MONTH)
            )}, ${c.get(Calendar.YEAR)}"
        }
    }
}