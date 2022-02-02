package com.example.haritagedemo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HeritageData(
    @SerializedName("site_name")
    val heritageTitle: String? = null,


    @SerializedName("category")
    val heritageSubTitle: String? = null,

    @SerializedName("id")
    val heritageId: Int? = 0,

    val heritageMajor: Int? = 0,

    val heritageMinor: Int? = 0,
    val heritageUuid: String? = null,
    @SerializedName("category_image")
    val heritageImageUrl: String? = null,

    @SerializedName("latitude")
    val heritageLatitude: String? = null,

    @SerializedName("longitude")
    val heritageLongitude: String? = null,

    var distance: Float? = 0f,
    @SerializedName("type")
    val heritageType: String? = null,
    var entryTime: String? = null,
    var exitTime: String? = null,
    var socialmediaType: String? = null,
    var heritageTitleInHindi: String? = null,
    var heritageTitleInGujarati: String? = null
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    constructor(siteTitle: String, siteId: Int) :
            this(
                siteTitle,
                "",
                siteId,
                Int.MIN_VALUE,
                Int.MIN_VALUE,
                "",
                "",
                "",
                "",
                Float.MIN_VALUE,
                ""
            )

    constructor(siteTitle: String, siteType: String, siteId: Int) :
            this(
                siteTitle,
                "",
                siteId,
                Int.MIN_VALUE,
                Int.MIN_VALUE,
                "",
                "",
                "",
                "",
                Float.MIN_VALUE,
                siteType,
            )

    constructor(
        siteTitle: String,
        siteId: Int,
        latitude: String,
        longitude: String,
        major: Int,
        type: String
    ) :
            this(
                siteTitle,
                "",
                siteId,
                major,
                Int.MIN_VALUE,
                "",
                "",
                latitude,
                longitude,
                Float.MIN_VALUE,
                type
            )

    constructor(
        siteTitle: String,
        siteSubTitle: String,
        siteId: Int,
        siteUuid: String,
        siteMajor: Int,
        siteMinor: Int
    ) :
            this(
                siteTitle,
                siteSubTitle,
                siteId,
                siteMajor,
                siteMinor,
                siteUuid,
                "",
                "",
                "",
                Float.MIN_VALUE,
                ""
            )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeritageData

        if (heritageTitle != other.heritageTitle) return false
        if (heritageId != other.heritageId) return false
        if (heritageLatitude != other.heritageLatitude) return false
        if (heritageLongitude != other.heritageLongitude) return false
        if (heritageType != other.heritageType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = heritageTitle?.hashCode() ?: 0
        result = 31 * result + (heritageId ?: 0)
        result = 31 * result + (heritageLatitude?.hashCode() ?: 0)
        result = 31 * result + (heritageLongitude?.hashCode() ?: 0)
        result = 31 * result + (heritageType?.hashCode() ?: 0)
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(heritageTitle)
        parcel.writeString(heritageSubTitle)
        parcel.writeValue(heritageId)
        parcel.writeValue(heritageMajor)
        parcel.writeValue(heritageMinor)
        parcel.writeString(heritageUuid)
        parcel.writeString(heritageImageUrl)
        parcel.writeString(heritageLatitude)
        parcel.writeString(heritageLongitude)
        parcel.writeValue(distance)
        parcel.writeString(heritageType)
        parcel.writeString(entryTime)
        parcel.writeString(exitTime)
        parcel.writeString(socialmediaType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeritageData> {
        override fun createFromParcel(parcel: Parcel): HeritageData {
            return HeritageData(parcel)
        }

        override fun newArray(size: Int): Array<HeritageData?> {
            return arrayOfNulls(size)
        }
    }
}
