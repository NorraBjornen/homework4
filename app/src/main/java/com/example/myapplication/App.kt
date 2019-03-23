package com.example.myapplication

import android.app.Application
import android.arch.persistence.room.Room
import com.example.myapplication.database.NewsDatabase
import com.example.myapplication.database.NewsItem

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val newsDao = Room.databaseBuilder(
            applicationContext.applicationContext,
            NewsDatabase::class.java,
            "myApp.db").build().newsDao()

        Repository.set(newsDao)

        val news : ArrayList<NewsItem> = arrayListOf(
            NewsItem(
                1,
                "Аэрофотосъёмка",
                "Богачи и крестьяне",
                "Аэрофотосъёмка ландшафта уже выявила земли богачей и процветающих крестьян.",
                "17.03.2019"
            ),
            NewsItem(
                2,
                "Цирюльникъ",
                "Стрижка ёжиком",
                "Эй, цирюльникъ, ёжик выстриги, да щетину ряхи сбрей, феном вошь за печь гони!",
                "17.03.2019"
            ),
            NewsItem(
                3,
                "Цитрус",
                "Нелёгкая жизнь цитруса",
                "В чащах юга жил-был цитрус... — да, но фальшивый экземпляръ!",
                "16.03.2019"
            ),
            NewsItem(
                4,
                "Жлоб",
                "Юные съёмщицы",
                "Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф.",
                "15.03.2019"
            ),
            NewsItem(
                5,
                "Мэр",
                "Поедание щипцов",
                "— Любя, съешь щипцы, — вздохнёт мэр, — кайф жгуч.",
                "8.02.2019"
            ),
            NewsItem(
                6,
                "Граф",
                "Изъятие плюща",
                "Экс-граф? Плюш изъят. Бьём чуждый цен хвощ!",
                "8.02.2019"
            ),
            NewsItem(
                7,
                "Грач",
                "Мышь с хоботом",
                "Южно-эфиопский грач увёл мышь за хобот на съезд ящериц.",
                "8.02.2019"
            )
        )

        Thread{
            news.forEach{newsDao.insert(it)}
        }.start()
    }
}