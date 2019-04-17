package com.example.myapplication.model

import com.example.myapplication.model.database.NewsItem
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

enum class DAYS {
    TODAY,
    YESTERDAY,
    OTHER
}

val usualFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))

fun getDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return calendar.time
}

fun getTextDateFromMilliseconds(milliSeconds: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return usualFormat.format(calendar.time)
}

fun compareNewsItemsByDate(x : NewsItem, y : NewsItem):Int{
    return when {
        x.date > y.date -> -1
        x.date > y.date -> 1
        else -> 0
    }
}

fun compareTimeByMilliseconds(x : Long, y : Long):Int{
    return when {
        x > y -> -1
        x > y -> 1
        else -> 0
    }
}

fun getMonthNameFromNumber(num: Int): String {
    var month = "wrong number"
    val months = DateFormatSymbols().months
    if (num in 0 until 12)
        month = months[num]
    return month
}

fun whichDay(c : Calendar) : DAYS {
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