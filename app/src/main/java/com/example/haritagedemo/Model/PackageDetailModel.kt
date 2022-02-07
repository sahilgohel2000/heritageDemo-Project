package com.example.haritagedemo.Model

import com.example.haritagedemo.FieldNearbySitesLocation
import com.example.haritagedemo.RatingReview
import com.example.haritagedemo.ReviewCategory
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PackageDetailModel(

    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p>The Mughal rulers who followed Aurangzeb were weak and the Mughul Viceroys (Subas) were busy fighting amongst themselves and with the Marathas. This resulted in disorder in the country, and from 1737 to 1753, there was a joint rule of the Mughal Viceroy and the Peshwa over Ahmedabad. In 1753 the combined armies of Raghunath Rao and Damaji Gaeakwad overtook the citadel and brought an end of Mughal rule at Ahmedabad.</p>
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // 100
    @SerializedName("field_entry_registration_phone")
    var fieldEntryRegistrationPhone: String = "", // 5177746259
    @SerializedName("field_starting_location_name")
    var fieldStartingLocationName: FieldNearbySitesLocation? = null,
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "", // Kalupur, Ahmedabad International
    @SerializedName("field_nearest_bus_station_location")
    var fieldNearestBusStationLocation: String = "", // Adalaj gam, Geeta Mandir
    @SerializedName("field_nearest_train_station_location")
    var fieldNearestTrainStationLocation: String = "", // Kalupur
    @SerializedName("field_ratings_and_reviews_on")
    var fieldRatingsAndReviewsOn: Int = 0, // 1
    @SerializedName("field_related_website_link")
    var fieldRelatedWebsiteLink: ArrayList<RelatedLinkModel> = ArrayList(),
    @SerializedName("field_upload_train_schedule")
    var fieldUploadTrainSchedule: String = "",
    @SerializedName("field_book_cab_url")
    var fieldBookCabUrl: String = "",

    @SerializedName("field_book_now")
    var fieldBookNow: String = "",
    @SerializedName("field_enquire_about_package")
    var fieldEnquireAboutPackage: String = "",

    @SerializedName("field_socialmedia_sharing_on")
    var fieldSocialmediaSharingOn: Int = 0, // 1
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // The Mughal rulers who followed Aurangzeb were weak and the Mughul Viceroys (Subas) were busy fighting amongst themselves and with the Marathas. This resulted in disorder in the country, and from 1737 to 1753, there was a joint rule of the Mughal Viceroy and the Peshwa over Ahmedabad. In 1753 the combined armies of Raghunath Rao and Damaji Gaeakwad overtook the citadel and brought an end of Mughal rule at Ahmedabad.
    @SerializedName("field_timings_for_event")
    var fieldTimingsForEvent: String = "", // 18:15 To 18:45
    @SerializedName("field_total_time_required")
    var fieldTotalTimeRequired: String = "", // 01 Hour 15 Minutes TO 07 Hour 30 Minutes
    @SerializedName("field_toursim_package_categories")
    var fieldToursimPackageCategories: String = "", // Non-AMC package tours
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("field_walk_included_sites")
    var fieldWalkIncludedSites: ArrayList<FieldNearbySitesLocation?> = ArrayList(),

    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: String = "", // 23.022505
    @SerializedName("longitude")
    var longitude: String = "", // 72.5713621
    @SerializedName("nid")
    var nid: String = "", // 34
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("tourism_package_name")
    var tourismPackageName: String = "", // One Day Tour package
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(),
    @SerializedName("type")
    var type: String = "" // tourism_packages
):Serializable{

}
