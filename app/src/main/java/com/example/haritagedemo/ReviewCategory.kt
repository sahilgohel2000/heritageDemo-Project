package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewCategory(
    @SerializedName("id")
    var id: String = "", // 101
    @SerializedName("name")
    var name: String = "" // Access to the site
) : Serializable {
    var isSelected: Boolean = false
}
