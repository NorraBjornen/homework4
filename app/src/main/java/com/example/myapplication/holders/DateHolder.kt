package com.example.myapplication.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.myapplication.adapters.Adapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.date_item.view.*
import java.text.DateFormatSymbols
import java.util.*

class DateHolder(itemView: View, private val context : Context) : RecyclerView.ViewHolder(itemView){
    private enum class DAYS {
        TODAY,
        YESTERDAY,
        OTHER
    }

    private val txt = itemView.date_txt!!

    fun bind(dateText: String){
        val c = Calendar.getInstance()
        c.time = Adapter.usualFormat.parse(dateText)

        txt.text = when(whichDay(c)){
            DAYS.TODAY -> context.resources.getString(R.string.today)
            DAYS.YESTERDAY -> context.resources.getString(R.string.yesterday)
            DAYS.OTHER -> "${c.get(Calendar.DAY_OF_MONTH)} ${getMonthForInt(c.get(Calendar.MONTH))}, ${c.get(Calendar.YEAR)}"
        }
    }

    private fun getMonthForInt(num: Int): String {
        var month = "wrong number"
        val months = DateFormatSymbols().months
        if (num in 0 until 11)
            month = months[num]
        return month
    }

    private fun whichDay(c : Calendar) : DAYS {
        val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_YEAR, 0)

        if(today.get(Calendar.YEAR) == c.get(Calendar.YEAR)
            && today.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR))
            return DAYS.TODAY

        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)

        if (yesterday.get(Calendar.YEAR) == c.get(Calendar.YEAR)
            && yesterday.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR))
            return DAYS.YESTERDAY

        return DAYS.OTHER
    }
}