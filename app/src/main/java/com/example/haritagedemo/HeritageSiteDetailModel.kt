package com.example.haritagedemo

import com.example.haritagedemo.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeritageSiteDetailModel(

    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p class="rtejustify">The architecture of the complex is credited to Azam and Muazzam Khan; two Persian brothers who are buried in the <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Azam_and_Mauzzam_Khan%27s_Tomb" title="Azam and Mauzzam Khan's Tomb">tomb near Vasna, Ahmedabad</a>. The complex was originally spread over 72 acres, surrounded by elaborate gardens on all sides. Over time, human settlements came around it, eating into the gardens and reducing the area to 34 acres.</p><p class="rtejustify">Shaikh Ahmed Khattu Ganj Bakhsh of <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Anhilwad_Patan" title="Anhilwad Patan">Anhilwad Patan</a>, the friend and adviser of <a href="https://en.wikipedia.org/wiki/Ahmad_Shah_I" title="Ahmad Shah I">Ahmad Shah I</a>, retired to Sarkhej in his later life and died here in 1445. In his honour a tomb, begun in 1445 by <a href="https://en.wikipedia.org/wiki/Muhammad_Shah_II" title="Muhammad Shah II">Muhammad Shah II</a>, was, in 1451, finished by his son Qutbuddin <a href="https://en.wikipedia.org/wiki/Ahmad_Shah_II" title="Ahmad Shah II">Ahmad Shah II</a>. The next Sultan <a href="https://en.wikipedia.org/wiki/Mahmud_Begada" title="Mahmud Begada">Mahmud Begada</a> was fond of the place and expanded the complex greatly. He dug a large Sarkhej lake, surrounded it with cut stone steps, built on its south-west corner a splendid palace, and finally, opposite to the Ganj Baksh's tomb, raised a mausoleum for himself and his family, where he, his son <a href="https://en.wikipedia.org/wiki/Muzaffar_Shah_II" title="Muzaffar Shah II">Muzaffar Shah II</a> and his queen Rajbai are buried.</p>
    @SerializedName("field_best_time_to_visits")
    var fieldBestTimeToVisits: String = "", // January To Febuary
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // 100
    @SerializedName("field_entry_registration_link")
    var fieldEntryRegistrationLink: String = "", // http://google.com
    @SerializedName("field_entry_registration_phone_number")
    var fieldEntryRegistrationPhoneNumber: String = "", // 0792685998
    @SerializedName("field_heritage_sites_category")
    var fieldHeritageSitesCategory: ArrayList<String> = ArrayList(),
    @SerializedName("field_interest_category")
    var fieldInterestCategory: ArrayList<String> = ArrayList(),
    @SerializedName("field_is_video_available")
    var fieldIsVideoAvailable: String = "", // 1
    @SerializedName("field_most_popular_attraction")
    var fieldMostPopularAttraction: String = "", // Sarkhej was once a prominent centre of Sufi culture in the country, where influential Sufi saint Shaikh Ahmed Ganj Baksh lived.
    @SerializedName("field_nearby_restaurants_location")
    var fieldNearbyRestaurantsLocation: String = "", // 1
    @SerializedName("field_nearby_sites_location")
    var fieldNearbySitesLocation: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("field_nearby_tourism_guest_location")
    var fieldNearbyTourismGuestLocation: String = "", // 1
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "", // Sardar Patel, Ahmedabad International
    @SerializedName("field_nearest_bus_station_location")
    var fieldNearestBusStationLocation: String = "", // Geeta Mandir
    @SerializedName("field_nearest_train_station_location")
    var fieldNearestTrainStationLocation: String = "", // Kalupur, Maninagar
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(),
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // Shoes is not allowed
    @SerializedName("field_timings_for_site")
    var fieldTimingsForSite: String = "", // 02:00 To 12:45
    @SerializedName("field_total_time_required")
    var fieldTotalTimeRequired: String = "", // 03 Hour 15 Minutes TO 05 Hour 15 Minutes
    @SerializedName("field_upload_arphoto_video")
    var fieldUploadArphotoVideo: String = "", // http://183.87.214.71:8080/heritage/sites/default/files/heritage_ar_superimposing/file_example_MP4_480_1_5MG_0.mp4
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("heritage_audio")
    var heritageAudio: ArrayList<HeritageAudio> = ArrayList(),
    @SerializedName("heritage_site_name")
    var heritageSiteName: String = "", // Indian Institute of Mangement (Louis Khan)
    @SerializedName("translate_site_name")
    var translateName: String = "",
    @SerializedName("heritage_video")
    var heritageVideo: ArrayList<HeritageVideo> = ArrayList(),
    @SerializedName("is_favourite")
    var isFavourite: Int = 0, // 0
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.0329473
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.5327703
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("type")
    var type: String = "", // heritage_site
    @SerializedName("nid")
    var nid: String = "", // 59
    @SerializedName("field_upload_train_schedule")
    var fieldUploadTrainSchedule: String = "",
    @SerializedName("field_book_cab_url")
    var fieldBookCabUrl: String = "",
    @SerializedName("start_time")
    var startTime: Int = 0, // Sarkhej Roja
    @SerializedName("end_time")
    var endTime: Int = 0, // Sarkhej Roja
    @SerializedName("field_socialmedia_sharing_on")
    var fieldSocialmediaSharingOn: Int = 0, // 0
    @SerializedName("field_ratings_and_reviews_on")
    var fieldRatingsAndReviewsOn: Int = 0, // 0
    @SerializedName("field_virtual_tour_available")
    var fieldVirtualTourAvailable: Int = 0, // 1
    @SerializedName("field_ardirection_applicable")
    var fieldArdirectionApplicable: Int = 0, // 1
    @SerializedName("field_arphoto_superimposing_applicable")
    var fieldArphotoSuperimposingApplicable: Int = 0, // 1
    @SerializedName("field_audio_available")
    var fieldAudioAvailable: Int = 0 // 1
):Serializable {
}
