package com.example.myapplication.model.network

class NewsItemResponse (val resultCode : String, public val payload : ResponseItem){
    class ResponseItem (public var content: String)
}