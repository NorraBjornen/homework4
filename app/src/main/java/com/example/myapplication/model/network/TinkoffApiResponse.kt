package com.example.myapplication.model.network

import com.google.gson.annotations.SerializedName

data class TinkoffApiResponse<T>(
    @SerializedName("payload")
    val payload: T
) : java.io.Serializable