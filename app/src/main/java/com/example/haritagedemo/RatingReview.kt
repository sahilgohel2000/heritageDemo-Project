package com.example.haritagedemo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RatingReview(
    @SerializedName("average")
    var average: Float = 0.0f, // 4
    @SerializedName("five_star")
    var fiveStar: Float = 0.0f, // 0
    @SerializedName("four_star")
    var fourStar: Float = 0.0f, // 100
    @SerializedName("highest_rating_star")
    var highestRatingStar: Float = 0.0f, // 80
    @SerializedName("highest_rating_value")
    var highestRatingValue: Float = 0.0f, // 100
    @SerializedName("is_rating_given_by_user")
    var isRatingGivenByUser: Int = 0, // 0
    @SerializedName("one_star")
    var oneStar: Float = 0.0f, // 0
    @SerializedName("rating_sharing_threshold")
    var ratingSharingThreshold: Int = 0, // 60
    @SerializedName("three_star")
    var threeStar: Float = 0.0f, // 0
    @SerializedName("Total_no_of_user_given_rating")
    var totalNoOfUserGivenRating: String = "", // 2
    @SerializedName("two_star")
    var twoStar: Float = 0.0f // 0
):Serializable
