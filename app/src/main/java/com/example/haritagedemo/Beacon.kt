package com.example.haritagedemo

data class Beacon(
    val data: ByteArray?,
    var manufacturer: String?,
    val macAddress: String,
    val uuid: String,
    val major: Int,
    val minor: Int,
    var rssi: Int,
    var lastUpdated: Long = System.currentTimeMillis()
){
    override fun toString(): String {
        return StringBuilder(uuid).append(": ").append(rssi.toString()).append(',')
            .append((System.currentTimeMillis() - lastUpdated) / 1000).append(',').append(major)
            .append(',').append(minor)
            .toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Beacon
        if (this.uuid == other.uuid && this.major == other.major && this.minor == other.minor)
            return true
        return false
    }

    fun isEqual(beacon: Beacon): Boolean {
        return this.uuid == beacon.uuid && this.major == beacon.major && this.minor == beacon.minor
    }

    override fun hashCode(): Int {
        var result = data?.contentHashCode() ?: 0
        result = 31 * result + (manufacturer?.hashCode() ?: 0)
        result = 31 * result + macAddress.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + major
        result = 31 * result + minor
        return result
    }
}
