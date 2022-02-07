package com.example.haritagedemo.Model

import com.google.gson.annotations.SerializedName

data class RelatedLinkModel(
    @SerializedName("link")
    var link: String = "", // https://ahmedabadcity.gov.in
    @SerializedName("name")
    var name: String = "" // Smart City Ahmedabad Development Limited
)
