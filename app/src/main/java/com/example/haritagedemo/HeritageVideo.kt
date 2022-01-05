package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeritageVideo(
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String = "", // http://183.87.214.71:8080/heritage/sites/default/files/heritage_video_thumbnail/Guided-Photography-Tour-Heritage-City-Ahmedabad.jpg
    @SerializedName("video_url")
    var videoUrl: String = "" // http://183.87.214.71:8080/heritage/sites/default/files/heritage_video/Virtual1.mp4
): Serializable
