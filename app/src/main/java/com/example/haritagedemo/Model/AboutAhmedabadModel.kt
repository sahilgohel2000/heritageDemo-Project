package com.example.haritagedemo.Model

import com.google.gson.annotations.SerializedName

data class AboutAhmedabadModel(
    @SerializedName("description")
    var description: String = "", // <p>Law Garden is a public garden in the city of Ahmedabad, India. The market outside the garden is very famous for the handicraft goods sold by local people. The road at the side of the garden is filled with street hawkers selling all kinds of food items</p><p>handicraft goods sold by local people. The road at the side of the garden is filled with street hawkers selling all kinds of food items.</p><p>The Law Garden eatery market would be regularized. The standing committee has asked the municipal commissioner to get the design and policy prepared. The regularization will help generate employment and will help the civic body to keep a close watch on the quality of food served there</p>
    @SerializedName("field_best_time_to_visits")
    var fieldBestTimeToVisits: String = "", // January To November
    @SerializedName("field_image_url")
    var fieldImageUrl: ArrayList<String> = ArrayList(),
    @SerializedName("field_nearest_airport_location")
    var fieldNearestAirportLocation: String = "",
    @SerializedName("field_nearest_bus_station")
    var fieldNearestBusStation: String = "",
    @SerializedName("field_nearest_train_location")
    var fieldNearestTrainLocation: String = "",
    @SerializedName("field_related_website_link")
    var fieldRelatedWebsiteLink: ArrayList<WebsiteLinkModel> = ArrayList(),
    @SerializedName("field_time_required")
    var fieldTimeRequired: String = "", // 02:15
    @SerializedName("language")
    var language: String = "", // en
    @SerializedName("status")
    var status: String = "", // 1
    @SerializedName("thinks_to_know")
    var thinksToKnow: String = "", // handicraft goods sold by local people. The road at the side of the garden is filled with street hawkers selling all kinds of food items.The Law Garden eatery market would be regularized. The standing committee has asked the municipal commissioner to get the design and policy prepared. The regularization will help generate employment and will help the civic body to keep a close watch on the quality of food served there
    @SerializedName("title")
    var title: String = "", // About Ahmedabad
    @SerializedName("type")
    var type: String = "" // about_ahmedabad

)
