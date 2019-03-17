package com.example.myapplication

import kotlin.collections.ArrayList

class NewsItem (val id : Int, val name : String, val summary : String, val content : String, val date: String) {
    companion object {
        var news : ArrayList<NewsItem> = arrayListOf(
            NewsItem(0,"Аэрофотосъёмка","Богачи и крестьяне","Аэрофотосъёмка ландшафта уже выявила земли богачей и процветающих крестьян.", "17.03.2019"),
            NewsItem(1,"Цирюльникъ","Стрижка ёжиком","Эй, цирюльникъ, ёжик выстриги, да щетину ряхи сбрей, феном вошь за печь гони!", "17.03.2019"),
            NewsItem(2,"Цитрус","Нелёгкая жизнь цитруса","В чащах юга жил-был цитрус... — да, но фальшивый экземпляръ!", "16.03.2019"),
            NewsItem(3,"Жлоб","Юные съёмщицы","Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф.", "15.03.2019"),
            NewsItem(4,"Мэр","Поедание щипцов","— Любя, съешь щипцы, — вздохнёт мэр, — кайф жгуч.", "8.02.2019"),
            NewsItem(5,"Граф","Изъятие плюща","Экс-граф? Плюш изъят. Бьём чуждый цен хвощ!", "8.02.2019"),
            NewsItem(6,"Грач","Мышь с хоботом","Южно-эфиопский грач увёл мышь за хобот на съезд ящериц.", "8.02.2019")
        )

        val recentIds : ArrayList<Int> = arrayListOf(0,1,2,3,4,5,6)
        var favouriteIds : ArrayList<Int> = ArrayList()
        var favouritesChanged = false
    }
}