package com.example.haritagedemo.API

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.example.haritagedemo.Activity.EventDetailActivity
import com.example.haritagedemo.Activity.FestivalDetailActivity
import com.example.haritagedemo.Activity.HeritageSiteDetailActivity
import com.example.haritagedemo.Activity.LocalCuisineActivity
import java.math.BigDecimal

object Util {

    fun getDistance(location1: Location): Float {
        var distance = location1.distanceTo(location1) / 1000
        var addedKm = Math.ceil((distance / 6).toDouble())
        var totalKm = distance + addedKm
        return round(totalKm.toFloat(), 2)
    }

    fun getLocation(lat: Double, long: Double): Location {
        var location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = lat
        location.longitude = long
        return location
    }

    fun round(d: Float, decimalPlace: Int): Float {
        var bd = BigDecimal(java.lang.Float.toString(d))
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        return bd.toFloat()
    }

    fun openDetailsScreen(mContext: Context, type: String, dataId: String?) {
        when (type) {
            Const.HERITAGETYPE.HERITAGE_SITE.toString().toLowerCase() -> {
                HeritageSiteDetailActivity.startActivity(mContext, dataId)
            }
        }
    }

//    fun openDetailsScreen(mContext: Context, type: String, nid: String?) {
//        when(type){
//            Const.HERITAGETYPE.HERITAGE_SITE.toString().toLowerCase()->{
//            HeritageSiteDetailActivity.startActivity(mContext,nid)
//            }
//            Const.HERITAGETYPE.EVENTS_ENTERTAINMENT.toString().toLowerCase()->{
//            EventDetailActivity.startActivity(mContext,nid)
//            }
//            Const.HERITAGETYPE.LOCAL_CUISINE.toString().toLowerCase()->{
//            LocalCuisineActivity.startActivity(mContext,nid)
//            }
//            Const.HERITAGETYPE.FESTIVALS.toString().toLowerCase()->{
//            FestivalDetailActivity.startActivity(mContext,nid)
//            }
//        }
//    }

}