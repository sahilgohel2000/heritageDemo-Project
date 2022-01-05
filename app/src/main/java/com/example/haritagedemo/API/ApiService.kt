package com.example.haritagedemo.API

import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.FestivalDetailModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.haritagedemo.Model.HeritageSiteDetailModel

interface ApiService {
    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_HERITAGE_SITE_DETAIL)
    fun apiHeritageSiteDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<HeritageSiteDetailModel>>

    // call Api For get Festival Details
    @FormUrlEncoded
    @POST(Api.API_GET_FESTIVAL_DETAIL)
    fun apiGetFestivalDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<FestivalDetailModel>>


    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_EVENT_DETAIL)
    fun apiEventDetail(@FieldMap param: HashMap<String, Any?>): Call<Response<EventDetailModel>>

    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_LOCAL_CUISINE_DETAIL)
    fun apiLocalCuisineDetail(@FieldMap param: HashMap<String, Any?>): Call<Response<LocalCuisineDetail>>
}
