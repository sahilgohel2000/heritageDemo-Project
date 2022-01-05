package com.example.haritagedemo.API

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.haritagedemo.API.Response

interface ApiService {
    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_HERITAGE_SITE_DETAIL)
    fun apiHeritageSiteDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<HeritageSiteDetailModel>>

}