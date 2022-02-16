package com.example.haritagedemo.API

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.haritagedemo.Activity.EventDetailActivity
import com.example.haritagedemo.Activity.FestivalDetailActivity
import com.example.haritagedemo.Activity.HeritageSiteDetailActivity
import com.example.haritagedemo.Activity.LocalCuisineActivity
import java.lang.Exception
import java.math.BigDecimal

object Util {

    fun getDistance(location1: Location, location2: Location): Float {
        // return  (Math.round((location1.distanceTo(location2) / 1000) * 10.0) / 10.0).toFloat()
        var distance = location1.distanceTo(location2) / 1000
        var addedKm = Math.ceil((distance / 6).toDouble())
        var totalKm = distance + addedKm
        return round(totalKm.toFloat(), 2)
    }

    fun round(d: Float, decimalPlace: Int): Float {
        var bd = BigDecimal(java.lang.Float.toString(d))
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        return bd.toFloat()
    }

    fun getLocation(lat: Double, long: Double): Location {
        var location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = lat
        location.longitude = long
        return location
    }

    fun openDetailsScreen(mContext: Context, type: String, dataId: String?) {
        when (type) {
            Const.HERITAGETYPE.HERITAGE_SITE.toString().toLowerCase() -> {
                HeritageSiteDetailActivity.startActivity(mContext, dataId)
            }
            Const.HERITAGETYPE.EVENTS_ENTERTAINMENT.toString().toLowerCase()->{
                EventDetailActivity.startActivity(mContext, dataId)
            }
            Const.HERITAGETYPE.LOCAL_CUISINE.toString().toLowerCase()->{
                LocalCuisineActivity.startActivity(mContext, dataId)
            }
            Const.HERITAGETYPE.FESTIVALS.toString().toLowerCase()->{
                FestivalDetailActivity.startActivity(mContext, dataId)
            }
        }
    }

    fun loadImageUrl(
        mContext: Context,
        imageUrl:String?,
        placeHolder: Int?,
        imageView: ImageView
    ) {
        try {
            if (placeHolder != null) {
                val requestOptions = RequestOptions()
                    .placeholder(placeHolder)
                    /*.signature(ObjectKey(File(imageUrl).lastModified()))*/
                    .error(placeHolder)

                Glide.with(mContext).load(imageUrl)
                    .apply(requestOptions)
                    .into(imageView)
            } else {
                Glide.with(mContext).load(imageUrl)
                    .into(imageView)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

//    /**
//     * load image resource
//     * */
//    fun loadImageResource(mContext: Context, imageResourceId: Int, imageView: ImageView) {
//        Glide.with(mContext).load(imageResourceId).into(imageView)
//    }
//
//    /**
//     * load image bitmap
//     * */
//    fun loadImageBitmap(
//        mContext: Context,
//        bitmap: Bitmap?,
//        placeHolder: Int,
//        imageView: ImageView
//    ) {
//        Glide.with(mContext).load(bitmap)
//            .apply(RequestOptions().placeholder(placeHolder).error(placeHolder))
//            .into(imageView)
//    }
//
//    private fun getUrlWithHeaders(url: String, token: String): GlideUrl {
//        return GlideUrl(
//            url, LazyHeaders.Builder()
//                .addHeader("Authorization", token)
//                .build()
//        )
//    }

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