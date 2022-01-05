package com.example.haritagedemo.API

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("success")
    val isSuccess: Boolean = false,
    @SerializedName("msg")
    var message: String = "Something went wrong.",
    @SerializedName("code")
    var code: Int = 0,
    @SerializedName("result")
    var result: T? = null
)
