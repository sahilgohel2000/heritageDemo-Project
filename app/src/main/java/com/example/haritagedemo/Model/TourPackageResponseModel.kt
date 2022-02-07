package com.example.haritagedemo.Model

import com.google.gson.annotations.SerializedName

data class TourPackageResponseModel(
    @SerializedName("description")
    var description: String = "", // Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
    @SerializedName("packages")
    var packages: List<TourPackageModel?> = listOf()
)
