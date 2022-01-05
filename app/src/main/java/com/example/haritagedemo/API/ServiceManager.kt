package com.example.haritagedemo.API

import android.content.Context
import android.net.Uri
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceManager(private val mContext: Context) {

    private val mPreferanceManager:PreferanceManager=PreferanceManager(mContext)

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(ConnectivityIntercepter(mContext))
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(ConnectivityIntercepter(mContext))

    val builder = OkHttpClient.Builder()
        .addInterceptor(ConnectivityIntercepter(mContext))
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(ConnectivityIntercepter(mContext))

    private fun buildApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(getUrl(Api.MAINURL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun addKey(params: HashMap<String, Any?>): HashMap<String, Any?> {
        params["key"] = Const.API_KEY
        return params
    }

    fun apiHeritageSiteDetails(
        params: HashMap<String, Any?>,
        l: ResponseListener<Response<HeritageSiteDetailModel>>
    ) {

        val call = buildApi().apiHeritageSiteDetails(addKey(params))
        call.enqueue(object : Callback<Response<HeritageSiteDetailModel>> {
            override fun onResponse(
                call: Call<Response<HeritageSiteDetailModel>>,
                response: retrofit2.Response<Response<HeritageSiteDetailModel>>
            ) {
                val body = response.body()
                if (body != null) {
                    if (body.code == Const.SUCCESS)
                        l.onRequestSuccess(response.body()!!)
                    else
                        l.onRequestFailed(body.message)
                } else
                    l.onRequestFailed(response.message())

            }

            override fun onFailure(call: Call<Response<HeritageSiteDetailModel>>, t: Throwable) {
                l.onRequestFailed(t)
            }
        })
    }

    fun getUrl(URL: APIURL): String {
        val builder = Uri.Builder()
        builder.scheme(Api.SCHEME)
        when (URL) {
            APIURL.DEVELOPER -> builder.encodedAuthority(Api.AUTHORITY_DEVELOPER)
            //  APIURL.PRODUCTION -> builder.encodedAuthority(Api.AUTHORITY_PRODUCTION)
        }
        builder.appendEncodedPath(Api.PATH1)
        builder.appendEncodedPath(Api.PATH)
        return builder.build().toString()
    }

}