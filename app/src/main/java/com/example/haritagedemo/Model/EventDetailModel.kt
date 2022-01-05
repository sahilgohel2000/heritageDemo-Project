package com.example.haritagedemo.Model

import com.example.haritagedemo.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventDetailModel(
    @SerializedName("amenities")
    var amenities: ArrayList<String> = ArrayList(),
    @SerializedName("field_interest_category")
    var fieldInterestCategory: ArrayList<String> = ArrayList(),
    @SerializedName("field_heritage_sites_category")
    var fieldHeritageSitesCategory: ArrayList<String> = ArrayList(),
    @SerializedName("description")
    var description: String = "", // <p>Every year, <a href="https://en.wikipedia.org/wiki/Gujarat" title="Gujarat">Gujarat</a> celebrates more than 200 festivals. The <strong>International Kite Festival (Uttarayan)</strong> is regarded as one of the biggest festivals celebrated.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-1">[1]</a></sup> Months before the festival, homes in Gujarat begin to manufacture kites for the festival.</p><p>The festival of <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Uttarayan" title="Uttarayan">Uttarayan</a> marks the day when winter begins to turn into summer, according to the <a href="https://en.wikipedia.org/wiki/Hindu_calendar" title="Hindu calendar">Indian calendar</a>. It is the sign for farmers that the sun is back and that harvest season is approaching which is called <a class="mw-redirect" href="https://en.wikipedia.org/wiki/Makara_Sankranti" title="Makara Sankranti">Makara Sankranti</a>/Mahasankranti. This day is considered to be one of the most important harvest day in India as It also marks the termination of the Winter season and the beginning of a new harvest season. Many cities in Gujarat organize kite competition between their citizens where the people all compete with each other. In this region of Gujarat and many other states, Uttarayan is such a huge celebration that it has become a <a href="https://en.wikipedia.org/wiki/Public_holidays_in_India#Hindu_holidays" title="Public holidays in India">public holiday in India</a> for two days.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-2">[2]</a></sup> During the festival, local food such as <a href="https://en.wikipedia.org/wiki/Undhiyu" title="Undhiyu">Undhiyu</a> (a mixed vegetable including yam and beans), sesame seed brittle and <a href="https://en.wikipedia.org/wiki/Jalebi" title="Jalebi">Jalebi</a> is served to the crowds.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-3">[3]</a></sup><sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-4">[4]</a></sup> Days before the festival, the market is filled with participants buying their supplies. In 2012, the Tourism Corporation of Gujarat mentioned that the International Kite Festival in Gujarat was attempting to enter the <a href="https://en.wikipedia.org/wiki/Guinness_World_Records" title="Guinness World Records">Guinness World Records</a> book due to the participation of 42 countries in it that year.</p><p>The International Kite Festival takes place in specially in Ahmedabad, <a href="https://en.wikipedia.org/wiki/Gujarat" title="Gujarat">Gujarat</a>, <a href="https://en.wikipedia.org/wiki/India" title="India">India</a>. The festival is called <a class="external text" href="http://sheokhanda.wordpress.com/2010/07/16/uttarayan-%E2%80%93-the-international-kite-festival/" rel="nofollow">Uttarayan</a>. The festival is celebrated in many cities of Gujarat, Telangana and Rajasthan like Ahmedabad, Jaipur, Udaipur, Jodhpur, <a href="https://en.wikipedia.org/wiki/Surat" title="Surat">Surat</a>, <a href="https://en.wikipedia.org/wiki/Vadodara" title="Vadodara">Vadodara</a>, <a href="https://en.wikipedia.org/wiki/Rajkot" title="Rajkot">Rajkot</a>, <a href="https://en.wikipedia.org/wiki/Hyderabad" title="Hyderabad">Hyderabad</a>, <a href="https://en.wikipedia.org/wiki/Nadiad" title="Nadiad">Nadiad</a>, <a href="https://en.wikipedia.org/wiki/Dakor" title="Dakor">Dakor</a>. However, the International Kite Event takes place in <a href="https://en.wikipedia.org/wiki/Ahmedabad#Festivals" title="Ahmedabad">Ahmedabad</a> (Kite capital of Gujarat) which accommodates visitors from many international destinations.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-6">[6]</a></sup><sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-7">[7]</a></sup></p><p>The best place to enjoy this festival is the <a href="https://en.wikipedia.org/wiki/Sabarmati_Riverfront" title="Sabarmati Riverfront">Sabarmati Riverfront</a> (its sabarmati river bank with capacity of over 500,000 people) <sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-8">[8]</a></sup> or the Ahmedabad Police Stadium, where people lay down to see the sky filled with thousands of kites <sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-9">[9]</a></sup></p><p>During the festival week the markets are flooded with kite buyers and sellers. In the heart of Ahmedabad, there is one of the most famous Kite markets - <em>Patang Bazaar</em>, which during the festive week opens 24 hours a day with buyers and sellers negotiating and buying in bulk.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-10">[10]</a></sup></p><p>Moreover, many families in Ahmadabad start making kites at home and setup small shops in their own homes.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-11">[11]</a></sup></p><p>There is also a Kite Museum, located at Sanskar Kendra in <a href="https://en.wikipedia.org/wiki/Paldi" title="Paldi">Paldi</a> area of Ahmedabad. Established in 1985, it contains a collection of unique kites.<sup><a href="https://en.wikipedia.org/wiki/International_Kite_Festival_in_Gujarat_%E2%80%93_Uttarayan#cite_note-12">[12]</a></sup></p><p>Other parts of India also celebrate kite festival. In Delhi on 15 August and most of Bihar's districts on 14 April. It is said because they just prepare new crop wheat. People offer prayers, eat <a href="https://en.wikipedia.org/wiki/Sattu" title="Sattu">Sattu</a> (made from new crop wheat) and new mangoes (baby mango also known as <em>Tikola</em>).</p>
    @SerializedName("event_name")
    var eventName: String = "", // Ahmedabad's Event kite festival
    @SerializedName("field_book_cab_url")
    var fieldBookCabUrl: String = "",
    @SerializedName("field_register_for_event")
    var fieldRegisterForEvent: String = "",
    @SerializedName("field_entry_fee_booking_info")
    var fieldEntryFeeBookingInfo: String = "", // 500 per person
    @SerializedName("field_entry_registration_phone")
    var fieldEntryRegistrationPhone: String = "", // 0792685998
    @SerializedName("field_is_video_available")
    var fieldIsVideoAvailable: String = "", // 1
    @SerializedName("field_location_of_event")
    var fieldLocationOfEvent: String = "", // L.D Arts College
    @SerializedName("field_nearby_sites_location")
    var fieldNearbySitesLocation: ArrayList<FieldNearbySitesLocation> = ArrayList(),
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "", // Terminal T1, Terminal T2
    @SerializedName("field_nearest_bus_station_location")
    var fieldNearestBusStationLocation: String = "", // Geeta Mandir, Laldarwaja, Ranip
    @SerializedName("field_nearest_train_station_location")
    var fieldNearestTrainStationLocation: String = "", // Kalupur, Gandhigram, Maninagar
    @SerializedName("field_review_category")
    var reviewCategory: ArrayList<ReviewCategory> = ArrayList(),
    @SerializedName("field_special_consideration")
    var fieldSpecialConsideration: String = "", // Every year, Gujarat celebrates more than 200 festivals. The International Kite Festival (Uttarayan) is regarded as one of the biggest festivals celebrated.[1] Months before the festival, homes in Gujarat begin to manufacture kites for the festival.
    @SerializedName("field_timings_for_event")
    var fieldTimingsForEvent: String = "", // 17:15 To 19:15,
    @SerializedName("field_total_time_required")
    var fieldTotalTimeRequired: String = "", // 08 Hour 30 Minutes TO 10 Hour 30 Minutes
    @SerializedName("field_upload_train_schedule")
    var fieldUploadTrainSchedule: String = "",
    @SerializedName("field_upload_url")
    var fieldUploadUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_upload_virtual_video_file")
    var fieldUploadVirtualVideoFile: String = "",
    @SerializedName("heritage_audio")
    var heritageAudio: ArrayList<HeritageAudio> = ArrayList(),
    @SerializedName("heritage_site_name")
    var heritageSiteName: String = "", // Ahmedabad's Event kite festival
    @SerializedName("translate_site_name")
    var transLateName: String ="",
    @SerializedName("heritage_video")
    var heritageVideo: ArrayList<HeritageVideo> = ArrayList(),
    @SerializedName("is_favourite")
    var isFavourite: Int = 0, // 0
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("latitude")
    var latitude: Double = 0.0, // 23.0345055
    @SerializedName("longitude")
    var longitude: Double = 0.0, // 72.537196
    @SerializedName("rating_review")
    var ratingReview: RatingReview = RatingReview(),
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("type")
    var type: String = "", // events_entertainment
    @SerializedName("nid")
    var nid: String = "", // 59
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
):Serializable
