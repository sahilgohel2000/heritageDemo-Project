package com.example.haritagedemo.API

import com.example.haritagedemo.*
import com.google.gson.annotations.SerializedName

data class LocalCuisineDetail(

    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("field_interest_category")
    var fieldInterestCategory: ArrayList<String> = ArrayList(),
    @SerializedName("field_heritage_sites_category")
    var fieldHeritageSitesCategory: ArrayList<String> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p>dabeli recipe | kacchi dabeli recipe | kutchi dabeli with detailed photo and video recipe. basically a spicy snack food originating from kutch, gujarat. kacchi dabelidabeli recipe | kacchi dabeli recipe | kutchi dabeli with detailed photo and video recipe. kachhi dabeli is a spicy snack prepared from pav and dabeli stuffing. the stuffing is prepared by mixing boiled mashed potatoes and with other indian spices. these stuffings is then placed in between the pav with green chutney, imli chutney and red garlic chutney. dabeli is popular street food of gujarat and maharshtra, but very well popular all over india. it is usually served during breakfast and for evening snacks. basically, dabeli is steet food originated from gujarat. it was first sold in a place called kutch in gujarat. hence the snack got its name as kutchi dabeli. also, according to wiki, dabeli literally means “pressed” in gujarati language. probably, it is because the pav is stuffed, pressed and heated on pan before serving. also, today, you can easily find these dabelis sold one every streets and corner of gujarat. moreover, dabeli is also a popular street food in mumbai steets as well. kutchi dabeli furthermore, the advantage of dabeli recipe is, it can be prepared in jiffy. the dabeli stuffing or potato masala could prepared and stored for hours. moreover, even the chutneys used in this recipe has a good shelf life. hence, you can prepare it instantly, whenever you feel to eat it. just get the pav, stuff it with masala, heat it on pan and coat it with sev. that’s it, your spicy and tangy dabeli is ready to be served. also, if you like cheesy dabeli, than add some grated cheese while stuffing. cheesy dabeli too tastes great. in conclusion, do check my other indian street food collections. specifically, vada pav recipe, samosa recipe, bhel puri recipe, samosa chaat recipe. also, do check my other indian snacks recipe collections. especially, sabudana vada, cheesy stuffed mushroom, veg bonda recipe and goli baje recipe.</p>
    @SerializedName("field_ardirection_applicable")
    var fieldArdirectionApplicable: Int = 0, // 1
    @SerializedName("field_arphoto_superimposing_applicable")
    var fieldArphotoSuperimposingApplicable: Int = 0, // 1
    @SerializedName("field_audio_available")
    var fieldAudioAvailable: Int = 0, // 1
    @SerializedName("field_book_cab_url")
    var fieldBookCabUrl: String = "",
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // Kufra District, Libya
    @SerializedName("field_entry_registration_phone")
    var fieldEntryRegistrationPhone: String = "", // 0792685998
    @SerializedName("field_is_video_available")
    var fieldIsVideoAvailable: Int = 0, // 1
    @SerializedName("field_most_popular_attraction")
    var fieldMostPopularAttraction: String = "", //     Chole Bhature. The capital city of India is famous throughout the country for serving the best varieties of Chole Bhature to the patrons at reasonably charged rates. ...    Poha Jalebi. ...    Chaats. ...    Idli Sambar / Dosa Chutney. ...    Bhajiya.
    @SerializedName("field_nearby_sites_location")
    var fieldNearbySitesLocation: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "", // Sardar Patel T1, Sardar Patel T2
    @SerializedName("field_nearest_bus_station_location")
    var fieldNearestBusStationLocation: String = "", // Geeta Mandir, Laldarwaja, Ranip
    @SerializedName("field_nearest_train_station_location")
    var fieldNearestTrainStationLocation: String = "", // Kalupur, Gandhigram, Maninagar
    @SerializedName("field_ratings_and_reviews_on")
    var fieldRatingsAndReviewsOn: Int = 0, // 1
    @SerializedName("field_socialmedia_sharing_on")
    var fieldSocialmediaSharingOn: Int = 0, // 1
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // Tried chocolate pizza and it was so yummy.. ambience is good. You can enjoy a pleasant time here with soft music playing in d background.
    @SerializedName("field_timings_for_event")
    var fieldTimingsForEvent: String = "", // 05:30 To 05:30
    @SerializedName("field_total_time_required")
    var fieldTotalTimeRequired: String = "", // 07 Hour 15 Minutes TO 14 Hour 15 Minutes
    @SerializedName("field_upload_train_schedule")
    var fieldUploadTrainSchedule: String = "",
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("field_virtual_tour_available")
    var fieldVirtualTourAvailable: Int = 0, // 1
    @SerializedName("heritage_audio")
    var heritageAudio: ArrayList<HeritageAudio> = ArrayList(),
    @SerializedName("heritage_site_name")
    var heritageSiteName: String = "", // Ahmedabad's Food Truck Park
    @SerializedName("heritage_video")
    var heritageVideo: ArrayList<HeritageVideo> = ArrayList(),
    @SerializedName("is_favourite")
    var isFavourite: Int = 0, // 0
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.0235428
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.5885092
    @SerializedName("nid")
    var nid: String = "", // 59
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("tourism_package_name")
    var tourismPackageName: String = "", // Ahmedabad's Food Truck Park
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(), @SerializedName("start_time")
    var startTime: Int = 0, // Sarkhej Roja
    @SerializedName("end_time")
    var endTime: Int = 0, // Sarkhej Roja
    @SerializedName("type")
    var type: String = "", // local_cuisine
    @SerializedName("translate_site_name")
    var translateName: String = ""
)
