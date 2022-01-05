package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FieldNearbySitesLocation(
    @SerializedName("category")
    var category: String = "", // Modern era, Mughal era, Museums and Institutes
    @SerializedName("translate_site_name")
    var translateSiteName: String ="", //Translate Name For Passing It To Map
    @SerializedName("type")
    var type: String = "", // Modern era, Mughal era, Museums and Institutes
    @SerializedName("id")
    var id: String = "", // 6
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 27.1731
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 78.0421
    @SerializedName("site_name")
    var siteName: String = "", // Sarkhej Roja
    @SerializedName("sites_other_names")
    var siteKeyword: String = "", // Sarkhej Roja
    @SerializedName("start_time")
    var startTime: Int = 0, // Sarkhej Roja
    @SerializedName("end_time")
    var endTime: Int = 0, // Sarkhej Roja
    @SerializedName("title")
    var title: String = "", // Adalaj Stepwell
    var distance: Float = 0f, //0km
    var level: Double = 0.0
): Serializable {
    var isSelected: Boolean = false
    var isDelete: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return if (other is FieldNearbySitesLocation) {
            this.id == other.id
        } else
            false
    }

    override fun hashCode(): Int {
        var result = category.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + siteName.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + isSelected.hashCode()
        result = 31 * result + isDelete.hashCode()
        return result
    }
}
