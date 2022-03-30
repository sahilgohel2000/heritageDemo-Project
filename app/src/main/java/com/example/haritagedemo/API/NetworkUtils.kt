package com.example.haritagedemo.API

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.ParseException
import android.net.Uri
import com.example.haritagedemo.R
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object NetworkUtils {

    fun getUrl(URL:APIURL):String {
        val builder =Uri.Builder()
        builder.scheme(Api.SCHEME)

        when(URL){
            APIURL.DEVELOPER -> builder.encodedAuthority(Api.AUTHORITY_DEVELOPER)
        }
        builder.encodedPath(Api.PATH)
        builder.encodedPath(Api.PATH1)
        return builder.build().toString()
    }

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

    //handle Error Messages
    fun getErrorMessages(mContext: Context, error:Throwable):String{
        return when(error){
                is NoConnectivityException -> mContext.getString(R.string.error_no_internet)
                is UnknownHostException -> mContext.getString(R.string.request_error_server)
                is ConnectException -> mContext.getString(R.string.error_connect)
                is TimeoutException -> mContext.getString(R.string.request_error_time_out)
                is IOException -> mContext.getString(R.string.request_error_server)
                is IllegalStateException -> mContext.getString(R.string.request_error_network)
                is ParseException -> mContext.getString(R.string.request_error_parse)
            else -> error.message.takeIf { error.message != null }
                ?: mContext.getString(R.string.something_want_wrong)
        }
    }
}