package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeritageAudio(
    @SerializedName("audio_url")
    var audioUrl: String = "", // http://yahoo.com
    @SerializedName("audio_url_language")
    var audioUrlLanguage: String = "" // Gujarati
) : Serializable