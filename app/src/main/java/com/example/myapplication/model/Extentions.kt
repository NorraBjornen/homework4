package com.example.myapplication.model

import android.app.Activity
import android.text.Html
import android.widget.TextView
import android.widget.Toast

fun Activity.toastShort(text : String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Activity.toastLong(text : String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun TextView.setTextFromHtml(txt : String?) {
    text = Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT)
}