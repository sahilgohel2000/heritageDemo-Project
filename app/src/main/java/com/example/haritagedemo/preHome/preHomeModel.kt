package com.example.haritagedemo.preHome

class preHomeModel(
    val menuId: Int, val titleResourceId: String, val descStringId: Int, val imageResourceID: Int,
    var subMenu: ArrayList<navigationDrawerModel?>? = null
) {
    var isOpen: Boolean = false
    var count: String? = null

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        return if (other is preHomeModel){
            this.menuId == other.menuId
        }else
            false
    }
}