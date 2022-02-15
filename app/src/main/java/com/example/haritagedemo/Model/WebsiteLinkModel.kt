package com.example.haritagedemo.Model

import com.google.gson.annotations.SerializedName

data class WebsiteLinkModel(
    @SerializedName("link")
    var link: String = "", // http://en.wikipedia.org/wiki/Law_Garden
    @SerializedName("name")
    var name: String = "" // wekipidea
)
