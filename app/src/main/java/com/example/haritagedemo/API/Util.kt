package com.example.haritagedemo.API

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.CaseMap
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.webkit.ConsoleMessage
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
import java.lang.Exception
import java.math.BigDecimal
import java.util.*
import com.example.haritagedemo.API.AlertMessage

object Util {

    //check internetConnections
    fun isConnectedtoInternet(mContext: Context?):Boolean{
        return try {
            val cm = mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    fun message(mContext: Context?,message: String?){
        if (mContext != null && message != null && !message.trim().isEmpty()){
            Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()
        }
    }

    fun showMessage(title: String,message: String,mContext: Context){
        if (message.trim().isNotEmpty()){
            AlertMessage().showMessage(mContext, message, title)
        }
    }

    fun showMessage(mContext: Context?, message: String?){
        if (mContext!=null && message!=null && !message.trim().isEmpty())
        {
            AlertMessage().showMessage(mContext,message)
        }
    }

    fun getDistance(location1: Location, location2: Location): Float {
        Log.d("UtilClass","Util.get Distance1:"+location1.toString()+location2.toString())

        // return  (Math.round((location1.distanceTo(location2) / 1000) * 10.0) / 10.0).toFloat()
        var distance = location1.distanceTo(location2) / 1000
        Log.d("UtilClass","Util.get Distance2:"+distance.toString())

        var addedKm = Math.ceil((distance / 6).toDouble())
        Log.d("UtilClass","Util.get Distance2:"+addedKm.toString())

        var totalKm = distance + addedKm
        Log.d("UtilClass","Util.get Distance3:"+totalKm.toString())

        return round(totalKm.toFloat(), 2)
    }

    fun round(d: Float, decimalPlace: Int): Float {
        Log.d("UtilClass","Util.round1:"+d.toString()+decimalPlace.toString())

        var bd = BigDecimal(java.lang.Float.toString(d))
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
        Log.d("UtilClass","Util.round2:"+bd.toString())

        return bd.toFloat()
    }

    fun getLocation(lat: Double, long: Double): Location {
        Log.d("UtilClass","Util.get Location"+lat.toString()+long.toString())

        var location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = lat
        location.longitude = long
        Log.d("UtilClass","Util.get Location"+location.latitude.toString()+location.longitude.toString())

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
        mContext: Context,
        siteName: String,
        latitude: Double,
        longitude: Double,
        latLng: LatLng?
    ) {
        val dialog = Dialog(mContext!!)
        dialog.setContentView(R.layout.dialog_get_there)

        Objects.requireNonNull(dialog.window)!!
            .setLayout(
                mContext.resources.getDimensionPixelSize(R.dimen._300dp),
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
            getThereMode(mContext!!,siteName,"d")
        }

        tvPublicTransport.setOnClickListener {
            getThereMode(mContext!!,siteName,"trasit")
        }

        tvMyByk.setOnClickListener {
            openMyBikeApp(mContext)
        }

        tvUber.setOnClickListener {
            Util.openUberCab(
                mContext!!,
                siteName,
                latLng?.latitude,
                latLng?.longitude,
                latitude,
                longitude
            )
        }

        tvOla.setOnClickListener {
            Util.openBookCab(mContext!!, latLng!!.latitude,
                latLng.longitude,latitude,longitude)
        }

        tvWalking.setOnClickListener {
            getThereMode(mContext!!,siteName,"w")
        }

        tvCancel.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun openBookCab(
        mContext: Context,
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
        mContext: Context,
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

    private fun getThereMode(mContext: Context, msiteName: String, mode: String) {
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