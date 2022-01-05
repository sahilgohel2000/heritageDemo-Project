package com.example.haritagedemo.API

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityIntercepter(private val mContext: Context):Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}