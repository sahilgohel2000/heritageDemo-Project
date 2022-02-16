package com.example.haritagedemo.Model

import com.example.haritagedemo.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeritageWalkModel(

    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p class="rtejustify">The 600 years old city of Ahmedabad (1411 A.D) has some of the finest Indian-Islamic monuments and exquisite Hindu and Jain temples. Its carved wooden houses are another unique architectural tradition. To experience the glory of Ahmedabad it is necessary to walk through the `Walled City` and truly observe the nature of its rich and varied architecture, its art, religious places, its culture and traditions.With the purpose of unveiling this aspect of the city to the tourists and the citizens themselves The Heritage Walk of Ahmedabad was launched by the Amdavad Municipal Corporation (AMC) in association with CRUTA Foundation, an NGO, on 19th November 1997, during World Heritage Week.A special feature of Ahmedabad is the plan of the old city, comprising numerous `Pols`, self-contained neighborhoods, sheltering large numbers of people. Some of these `Pols` are virtually small villages, traversed by narrow lanes, usually terminating in squares (Chowks) comprising `Community Well` and `Chabutro` (for feeding birds). These `Pols` were protected by gates, cul-de-sacs and secret passages. These historic residential settlements are explained in detail during the walk.The walk commences from the early 19th century Swaminarayan temple Kalupur, encompasses 20 main spots besides numerous areas and aspects of the old city and concludes at the famous 15th century Jama Masjid. Hence the Walk is popularly known as the journey of `Mandir to Masjid`. The Walk, initiated more than one and a half decades ago, the first one of its kind by an Urban Local Body (ULB), has been continuing its journey with an unbroken record of 15 years and has lived up to its purpose of reviving the old city through rediscovery. The Ahmedabad model of Heritage Walk is being replicated in many other historic cities and towns of India like Jaipur, Amritsar, Lucknow etc. to conserve heritage and promote tourism.Adding dignity to what is old and giving it a place of pride in the city is the purpose behind the Heritage Walk conducted by Amdavad Municipal Corporation.</p><p class="rtejustify"><strong>Details of the Walk</strong><br /><br /><strong>Starting Point :</strong>&nbsp;Swaminarayan Temple, Kalupur, Off Relief Road, Ahmedabad.<br /><strong>Number of Pause Points:</strong> 20 Points<br /><strong>Length:</strong> 2 km<br /><strong>Duration:</strong> 2 Hrs. 30 Min. (Including Slide Show)<br /><strong>Time:</strong> 7:45 A.M. for slide show followed by walk at 8:00 A.M.<br /><strong>Project Advisor:</strong> Akshar Group, AMC Heritage Department<br /><strong>Web Site:</strong> https://heritagewalkahmedabad.com</p>
    @SerializedName("field_book_cab_url")
    var fieldBookCabUrl: String = "",
    @SerializedName("field_book_now")
    var fieldBookNow: String = "",
    @SerializedName("field_enquire_about_walk")
    var fieldEnquireAboutWalk: String = "",
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // 100
    @SerializedName("field_entry_registration_link")
    var fieldEntryRegistrationLink: String = "", // https://ahmedabadcity.gov.in/portal/jsp/Static_pages/heritage_walk.jsp
    @SerializedName("field_entry_registration_phone_number")
    var fieldEntryRegistrationPhoneNumber: String = "", // 0267425389
    @SerializedName("field_image_url")
    var fieldImageUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_is_video_available")
    var fieldIsVideoAvailable: String = "", // 1
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "", // Ahmedabad Airport
    @SerializedName("field_nearest_bus_station_location")
    var fieldNearestBusStationLocation: String = "", // Gujarat State Road Transport Corporation in Geeta Mandir Road, Ahmedabad is one of the top ST Bus Depot
    @SerializedName("field_nearest_train_station_location")
    var fieldNearestTrainStationLocation: String = "", // Ahmedabad Junction railway station -  Locally, people refer to it as Kalupur Station (as it is situated in the Kalupur area of the walled city)
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // Dress Code:-The Mandir is a sacred house of God and a place of daily worship. To preserve its sanctity andSpiritual ambience, a strict dress code should be observed within the complex.Upper Wear: Must cover the shoulders, chest, navel, and upper armsLower Wear: Must be at least below knee-length.Cancellation & Refund PolicyNo refund to be made once you make the booking
    @SerializedName("field_timings_for_walk")
    var fieldTimingsForWalk: String = "", // 07:45 To 10:30
    @SerializedName("field_total_time_required")
    var fieldTotalTimeRequired: String = "", // 02 Hour TO 02 Hour 30 Minutes
    @SerializedName("field_upload_arphoto_video")
    var fieldUploadArphotoVideo: String = "", // http://183.87.214.71:8080/heritage/sites/default/files/heritage_ar_superimposing/SampleVideo_1280x720_1mb.mp4
    @SerializedName("field_upload_train_schedule")
    var fieldUploadTrainSchedule: String = "",
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("field_walk_category")
    var fieldWalkCategory: String = "", // Morning Heritage Walk
    @SerializedName("field_walk_included_sites")
    var fieldWalkIncludedSites: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("field_walk_nearby_sites_location")
    var fieldWalkNearbySitesLocation: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("heritage_audio")
    var heritageAudio: ArrayList<HeritageAudio> = ArrayList(),
    @SerializedName("heritage_video")
    var heritageVideo: ArrayList<HeritageVideo> = ArrayList(),
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.033863
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.585022
    @SerializedName("name_of_walk")
    var nameOfWalk: String = "", // Morning Heritage walk ahmedabad
    @SerializedName("nid")
    var nid: String = "", // 23
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("type")
    var type: String = "", // heritage_walk
    @SerializedName("walk_category_id")
    var walkCategoryId: String = "", // 23
    @SerializedName("walk_category_name")
    var walkCategoryName: String = "", // Morning Heritage Walk
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(),

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

):Serializable{

}
