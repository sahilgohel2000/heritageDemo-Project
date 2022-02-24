package com.example.haritagedemo.preHome

class navigationDrawerModel(
    val menuId: Int, val titleResourceId: Int, val imageResourceID: Int,
    var subMenu: ArrayList<navigationDrawerModel?>? = null
) {
    var isOpen: Boolean = false
    var count: String? = null

    override fun equals(other: Any?): Boolean {

        if (other == null) return false
        return if (other is navigationDrawerModel){
            this.menuId == other.menuId
        }else
            false

//        return super.equals(other)
    }
}