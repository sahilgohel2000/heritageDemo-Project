package com.example.haritagedemo.API

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.haritagedemo.Activity.EventDetailActivity
import com.example.haritagedemo.Activity.FestivalDetailActivity
import com.example.haritagedemo.Activity.HeritageSiteDetailActivity
import com.example.haritagedemo.Activity.LocalCuisineActivity
import com.example.haritagedemo.R
import com.google.android.gms.maps.model.LatLng
import org.w3c.dom.Text
import java.lang.Exception
import java.math.BigDecimal
import java.util.*

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

    fun showGetThereDialog(
        heritageSiteDetailActivity: HeritageSiteDetailActivity,
        siteName: String,
        latitude: Double,
        longitude: Double,
        latLng: LatLng?
    ) {
        val dialog = Dialog(heritageSiteDetailActivity)
        dialog.setContentView(R.layout.dialog_get_there)

        Objects.requireNonNull(dialog.window)!!
            .setLayout(
                heritageSiteDetailActivity.resources.getDimensionPixelSize(R.dimen._300dp),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        val tvDriving = dialog.findViewById<TextView>(R.id.tv_driving)
        val tvPublicTransport = dialog.findViewById<TextView>(R.id.tv_public_transport)
        val tvMyByk = dialog.findViewById<TextView>(R.id.tv_myByk)
        val tvUber = dialog.findViewById<TextView>(R.id.tv_Uber)
        val tvOla = dialog.findViewById<TextView>(R.id.tv_Ola)
        val tvWalking = dialog.findViewById<TextView>(R.id.tv_walking)

        val tvCancel = dialog.findViewById<TextView>(R.id.tv_Cancel)

        tvDriving.setOnClickListener {
            getThereMode(heritageSiteDetailActivity,siteName,"d")
        }

        tvPublicTransport.setOnClickListener {
            getThereMode(heritageSiteDetailActivity,siteName,"trasit")
        }

        tvMyByk.setOnClickListener {
            openMyBikeApp(heritageSiteDetailActivity)
        }

        tvUber.setOnClickListener {
            Util.openUberCab(
                heritageSiteDetailActivity,
                siteName,
                latLng?.latitude,
                latLng?.longitude,
                latitude,
                longitude
            )
        }

        tvOla.setOnClickListener {
            Util.openBookCab(heritageSiteDetailActivity, latLng!!.latitude,
                latLng.longitude,latitude,longitude)
        }

        tvWalking.setOnClickListener {
            getThereMode(heritageSiteDetailActivity,siteName,"w")
        }

        tvCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun openBookCab(
        mContext: HeritageSiteDetailActivity,
        currLat: Double?,
        currLng: Double?,
        dropLat: Double?,
        dropLng: Double?
    ) {
        val mIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://olawebcdn.com/assets/ola-universal-link.html?lat=$currLat&lng=$currLng&category=share&utm_source=xapp_token&landing_page=bk&drop_lat=$dropLat&drop_lng=$dropLng&affiliate_uid=12345")
        )
        mContext.startActivity(mIntent)
    }

    private fun openUberCab(
        mContext: HeritageSiteDetailActivity,
        nickName: String,
        currLat: Double?,
        currLng: Double?,
        dropLat: Double?,
        dropLng: Double?
    ) {
        val pm:PackageManager = mContext.packageManager
        try {
            pm.getPackageInfo("com.ubercab",PackageManager.GET_ACTIVITIES)
            val uri = "uber://?"+
                    "pickup[longitude]=$currLng&" +
                    "pickup[latitude]=$currLat&" +
                    "pickup[nickname]=$nickName" +
                    "dropoff[formatted_address]=$nickName, Ahmedabad, Gujarat, India&" +
                    "dropoff[latitude]=$dropLat&" +
                    "dropoff[longitude]=$dropLng&" +
                    "action=setPickup"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(uri)
            mContext.startActivity(intent)
        }catch (e:PackageManager.NameNotFoundException){
            try {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.ubercab")
                    )
                )
            }catch (anfe:ActivityNotFoundException){
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")
                    )
                )
            }
        }

    }

    private fun openMyBikeApp(mContext: Context) {
        var intent:Intent? = mContext.packageManager.getLaunchIntentForPackage("in.greenpedia.mybyk")

        if (intent != null){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
        }else{
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("market://details?id=" + "in.greenpedia.mybyk")
            mContext.startActivity(intent)
        }
    }

    private fun getThereMode(mContext: HeritageSiteDetailActivity, msiteName: String, mode: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?daddr=$msiteName&dirflg=$mode")
        )
        intent.setPackage("com.google.android.apps.maps")
        try {
            mContext.startActivity(intent)
        }catch (e:ActivityNotFoundException){
            try {
                val unrestrictedIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=$msiteName, Ahmedabad, Gujarat, India&travelmode=$mode")
                )
                mContext.startActivity(unrestrictedIntent)
            }catch (innerex:ActivityNotFoundException){
                innerex.printStackTrace()
            }
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