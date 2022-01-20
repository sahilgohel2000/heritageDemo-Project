package com.example.haritagedemo.Model

import com.google.gson.annotations.SerializedName

data class HelpModel(
    @SerializedName("helpline_number")
    var helplineNumber: ArrayList<String> = ArrayList(),
    @SerializedName("helpline_title")
    var helplineTitle: String = "", // Civil Hospital
    @SerializedName("icon")
    var icon: String = "", // http://183.87.214.71:8080/heritage/
    @SerializedName("kiosk_icon")
    var kioskIcon: String = "" // http://183.87.214.71:8080/heritage/
)
