package com.example.haritagedemo.API

import com.example.haritagedemo.FieldNearbySitesLocation
import com.example.haritagedemo.HeritageSiteDetailModel
import com.example.haritagedemo.Language
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
    @POST(Api.API_SEARCH_ITINERARY)
    fun apiSearchItinerary(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<FieldNearbySitesLocation?>>>

    // Api For get Heritage Walk Details
    @FormUrlEncoded
    @POST(Api.API_GET_HERITAGE_WALK_DETAIL)
    fun apiGetHeritageWalkDetail(@FieldMap param: HashMap<String, Any?>): Call<Response<HeritageWalkModel>>

    // Call Api For Language List
    @FormUrlEncoded
    @POST(Api.API_HERITAGE_LANGUAGE)
    fun apiGetLanguageList(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<Language?>>>

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

    // Call Api For get Related Link
    @FormUrlEncoded
    @POST(Api.API_GET_RELATED_LINK)
    fun apiGetRelatedLink(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<RelatedLinkModel?>>>

    //call api heritage Quiz......
    @FormUrlEncoded
    @POST(Api.API_GET_QUIZ)
    fun getHeritageQuiz(@FieldMap param: HashMap<String, Any?>): Call<Response<ArrayList<QuizData?>>>
}
