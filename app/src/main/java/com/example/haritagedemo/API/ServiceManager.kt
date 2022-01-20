package com.example.haritagedemo.API

import android.content.Context
import android.net.Uri
import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.FestivalDetailModel
import com.example.haritagedemo.Model.HelpModel
import com.example.haritagedemo.Model.HeritageSiteDetailModel
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


    /**
     *
     * Api For get site Details
     *
     * */

    fun apiLocalCuisineDetail(
        params: HashMap<String, Any?>,
        l: ResponseListener<Response<LocalCuisineDetail>>
    ) {

        val call = buildApi().apiLocalCuisineDetail(addKey(params))
        call.enqueue(object : Callback<Response<LocalCuisineDetail>> {
            override fun onResponse(
                call: Call<Response<LocalCuisineDetail>>,
                response: retrofit2.Response<Response<LocalCuisineDetail>>
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

            override fun onFailure(call: Call<Response<LocalCuisineDetail>>, t: Throwable) {
                l.onRequestFailed(t)
            }
        })
    }

    //CALL API EVENT DETAIL
    fun apiEventDetail(
        params: HashMap<String, Any?>,
        l: ResponseListener<Response<EventDetailModel>>
    ) {

        val call = buildApi().apiEventDetail(addKey(params))
        call.enqueue(object : Callback<Response<EventDetailModel>> {
            override fun onResponse(
                call: Call<Response<EventDetailModel>>,
                response: retrofit2.Response<Response<EventDetailModel>>
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

            override fun onFailure(call: Call<Response<EventDetailModel>>, t: Throwable) {
                l.onRequestFailed(t)
            }
        })
    }

    //API FOR GET FESTIVAL SITE
    fun apiGetFestivalDetails(
        params: HashMap<String, Any?>,
        l: ResponseListener<Response<FestivalDetailModel>>
    ) {

        val call = buildApi().apiGetFestivalDetails(addKey(params))
        call.enqueue(object : Callback<Response<FestivalDetailModel>> {
            override fun onResponse(
                call: Call<Response<FestivalDetailModel>>,
                response: retrofit2.Response<Response<FestivalDetailModel>>
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

            override fun onFailure(call: Call<Response<FestivalDetailModel>>, t: Throwable) {
                l.onRequestFailed(t)
            }
        })
    }

    //API for Call Helpline Numbers
    fun apiHelpLine(
        params: HashMap<String, Any?>,
        l: ResponseListener<retrofit2.Response<Response<ArrayList<HelpModel?>>>>
    ) {

        val call = buildApi().apiHelpLine(addKey(params))
        call.enqueue(object : Callback<Response<ArrayList<HelpModel?>>> {
            override fun onResponse(
                call: Call<Response<ArrayList<HelpModel?>>>,
                response: retrofit2.Response<Response<ArrayList<HelpModel?>>>
            ) {
                l.onRequestSuccess(response)
            }

            override fun onFailure(
                call: Call<Response<ArrayList<HelpModel?>>>,
                t: Throwable
            ) {
                l.onRequestFailed(t)
            }
        })
    }

    //API FOR HERITAGE SITE
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