package com.example.haritagedemo.Model

import com.example.haritagedemo.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FestivalDetailModel(
    @SerializedName("translate_site_name")
    var transLateName: String = "",
    @SerializedName("amenities")
    var amenities: ArrayList<Any> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p>Holi is a popular ancient Hindu festival, originating from the Indian subcontinent. It is celebrated predominantly in India, but has also spread to other areas of Asia and parts of the Western world through the diaspora from the Indian subcontinent.</p><p>Holi is an ancient Hindu religious festival which has become popular with non-Hindus as well in many parts of South Asia, as well as people of other communities outside Asia. In addition to India and Nepal, the festival is celebrated by Indian subcontinent diaspora in countries such as Jamaica,Suriname, Guyana, Trinidad and Tobago, South Africa, Malaysia, the United Kingdom, the United States, Canada, Mauritius, and Fiji.In recent years the festival has spread to parts of Europe and North America as a spring celebration of love, frolic, and colours.</p><p>Holi celebrations start on the night before Holi with a Holika Dahan where people gather, perform religious rituals in front of the bonfire, and pray that their internal evil be destroyed the way Holika, the sister of the demon king Hiranyakashipu, was killed in the fire. The next morning is celebrated as Rangwali Holi – a free-for-all festival of colours, where people smear each other with colours and drench each other. Water guns and water-filled balloons are also used to play and colour each other. Anyone and everyone is fair game, friend or stranger, rich or poor, man or woman, children, and elders. The frolic and fight with colours occurs in the open streets, open parks, outside temples and buildings. Groups carry drums and other musical instruments, go from place to place, sing and dance. People visit family, friends and foes to throw coloured powders on each other, laugh and gossip, then share Holi delicacies, food and drinks. Some customary&nbsp; (made from cannabis), which is intoxicating. In the evening, after sobering up, people dress up and visit friends and family.</p>
    @SerializedName("field_enable_festival_registration")
    var fieldEnableFestivalRegistration: String = "", // 0
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // 500 per person
    @SerializedName("field_entry_registration_link")
    var fieldEntryRegistrationLink: String = "", // http://google.com
    @SerializedName("field_entry_registration_phone_number")
    var fieldEntryRegistrationPhoneNumber: String = "", // 0792685998
    @SerializedName("field_festival_category")
    var fieldFestivalCategory: ArrayList<String> = ArrayList(),
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(),
    @SerializedName("field_select_special_celebration_locations")
    var fieldSelectSpecialCelebrationLocations: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // Bus traveling
    @SerializedName("field_timings_for_festival_at_celebration_location")
    var fieldTimingsForFestivalAtCelebrationLocation: String = "", // 01:00 AM TO 05:00 AM
    @SerializedName("field_timings_of_festival")
    var fieldTimingsOfFestival: String = "", // 10th January
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("heritage_audio")
    var heritageAudio: ArrayList<HeritageAudio> = ArrayList(),
    @SerializedName("heritage_site_name")
    var heritageSiteName: String = "", // Holi festival
    @SerializedName("heritage_video")
    var heritageVideo: ArrayList<HeritageVideo> = ArrayList(),
    @SerializedName("is_favourite")
    var isFavourite: Int = 0, // 0
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.046887
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.515398
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("type")
    var type: String = "", // festivals
    @SerializedName("nid")
    var nid: String = "", // 59
    @SerializedName("start_time")
    var startTime: Int = 0, // Sarkhej Roja
    @SerializedName("end_time")
    var endTime: Int = 0, // Sarkhej Roja
    @SerializedName("field_is_video_available")
    var fieldIsVideoAvailable: Int = 0, // 1
    @SerializedName("field_ratings_and_reviews_on")
    var fieldRatingsAndReviewsOn: Int = 0, // 0
    @SerializedName("field_socialmedia_sharing_on")
    var fieldSocialmediaSharingOn: Int = 0, // 1
    @SerializedName("field_audio_available")
    var fieldAudioAvailable: Int = 0, // 1
    @SerializedName("field_arphoto_superimposing_applicable")
    var fieldArphotoSuperimposingApplicable: Int = 0, // 1
    @SerializedName("field_virtual_tour_available")
    var fieldVirtualTourAvailable: Int = 0// 1
):Serializable{

}
