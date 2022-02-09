package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TourPackageModel(
    @SerializedName("id")
    var id: String = "", // 33
    @SerializedName("name")
    var name: String = "" // City Tour Packages
):Serializable{

}
