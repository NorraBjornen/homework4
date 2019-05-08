package com.example.myapplication.model.network

import com.google.gson.annotations.SerializedName

data class NewsItemDetails(

    @SerializedName("content")
    val content: String

) : java.io.Serializable

