package com.example.haritagedemo.API

import com.example.haritagedemo.Model.*
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.haritagedemo.QuizData

interface ApiService {
    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_HERITAGE_SITE_DETAIL)
    fun apiHeritageSiteDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<HeritageSiteDetailModel>>

    // call Api For get Festival Details
    @FormUrlEncoded
    @POST(Api.API_GET_FESTIVAL_DETAIL)
    fun apiGetFestivalDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<FestivalDetailModel>>

    //call Api for Tourism Package
    @FormUrlEncoded
    @POST(Api.API_GET_TOURISM_PACKAGE_DETAILS)
    fun apiGetTourismPackageDetails(@FieldMap param: HashMap<String, Any?>): Call<Response<PackageDetailModel>>

    // call Api For get About Ahmedabad
    @FormUrlEncoded
    @POST(Api.API_ABOUT_AHMEDABAD)
    fun apiAboutAhmedabad(@FieldMap param: HashMap<String, Any?>): Call<Response<AboutAhmedabadModel>>

    // Call Api For get Itinerary List
    @FormUrlEncoded
    @POST(Api.API_GET_TOURISM_PACKAGES)
    fun apiGetTourismPackages(@FieldMap param: HashMap<String, Any?>): Call<Response<TourPackageResponseModel>>

    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_EVENT_DETAIL)
    fun apiEventDetail(@FieldMap param: HashMap<String, Any?>): Call<Response<EventDetailModel>>

    // call Api For get site Details
    @FormUrlEncoded
    @POST(Api.API_GET_LOCAL_CUISINE_DETAIL)
    fun apiLocalCuisineDetail(@FieldMap param: HashMap<String, Any?>): Call<Response<LocalCuisineDetail>>

    // Call Api For get Itinerary List
    @FormUrlEncoded
    @POST(Api.API_GET_HELPLINE)
    fun apiHelpLine(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<HelpModel?>>>

    //call api heritage Quiz......
    @FormUrlEncoded
    @POST(Api.API_GET_QUIZ)
    fun getHeritageQuiz(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<QuizData?>>>
}
