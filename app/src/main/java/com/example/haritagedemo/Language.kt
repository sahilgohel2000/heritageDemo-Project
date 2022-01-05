package com.example.haritagedemo

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("code")
    var code: String = "", // en
    @SerializedName("name")
    var name: String = "" // English
) {
    var isSelected: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return if (other is Language) {
            this.code == other.code
        } else
            false
    }
}

