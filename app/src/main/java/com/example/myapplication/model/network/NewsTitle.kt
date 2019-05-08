package com.example.myapplication.model.network

import com.google.gson.annotations.SerializedName

data class NewsTitle(

    @SerializedName("id")
    var id: Int,

    @SerializedName("text")
    var text: String,

    @SerializedName("publicationDate")
    var publicationDate: PublicationDate

) : java.io.Serializable {

    class PublicationDate(
        @SerializedName("milliseconds")
        val milliseconds: Long
    ) : java.io.Serializable
}