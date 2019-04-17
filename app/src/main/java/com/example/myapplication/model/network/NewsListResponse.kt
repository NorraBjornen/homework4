package com.example.myapplication.model.network

class NewsListResponse (val resultCode : String, public val payload : List<ResponseItem>){
    class ResponseItem (
        var id: Int,
        var name: String,
        var text : String,
        var publicationDate: PublicationDate
    ) {
        class PublicationDate (val milliseconds : Long)
    }
}

