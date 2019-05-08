package com.example.myapplication.model

import android.content.Context
import android.support.v4.text.HtmlCompat
import android.text.Html
import android.widget.TextView
import android.widget.Toast

fun Context.toastShort(text : String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text : String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun TextView.setTextFromHtml(txt : String) {
    text = HtmlCompat.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT)
}