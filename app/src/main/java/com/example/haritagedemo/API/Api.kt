package com.example.haritagedemo.API

object Api {

    val MAINURL = APIURL.DEVELOPER

    const val SCHEME = "https"

    //    const val AUTHORITY_DEVELOPER = "192.168.10.105"
//    const val AUTHORITY_DEVELOPER = "192.168.30.75"
    const val AUTHORITY_DEVELOPER = "aha.nascentinfo.com"

    // const val AUTHORITY_PRODUCTION = "216.250.119.86"
    const val PATH = "api/"
    const val PATH1 = "heritage/"
    const val API_LOGIN = "login"
    const val API_CONFIRM_LOGIN_OTP = "confirm_login_otp"
    const val API_RESEND_OTP = "resend_otp"
    const val API_REGISTER_USER = "register_user"
    const val API_CONFIRM_REGISTER_OTP = "confirm_register_otp"
    const val API_LOGOUT = "logout"
    const val API_GET_USER_PROFILE = "getuserprofile"
    const val API_GET_COUNTRY_LIST = "getcountrylist"
    const val API_HERITAGE_LANGUAGE = "heritage_language"
    const val API_TOKEN_REGISTER = "token_register"
    const val API_GET_INTEREST_CATEGORY_LIST = "getinterestcategorylist"
    const val API_GET_INTEREST_ITINERARY_LIST = "getautomaticuserinterestcategorylist"
    const val API_GET_HERITAGE_SITE_DETAIL = "get_heritage_site_detail"
    const val API_GET_LOCAL_CUISINE_DETAIL = "get_local_cuisine_detail"
    const val API_GET_EVENT_DETAIL = "get_event_detail"
    const val API_GET_TOURISM_PACKAGE_DETAILS = "get_tourism_package_details"
    const val API_GET_FESTIVAL_DETAIL = "get_festival_detail"
    const val API_ABOUT_AHMEDABAD = "about_ahmedabad"
    const val API_GET_HERITAGE_WALK_DETAIL = "get_heritage_walk_detail"
    const val API_DELETE_ITINERARY = "delete_itinerary"
    const val API_DELETE_SITE_FROM_ITINERARY = "delete_site_from_itinerary"
    const val API_LIST_ITINERARY = "list_itinerary"
    const val API_ADD_TO_MY_ITINERARY = "add_to_my_itinerary"
    const val API_SEARCH_ITINERARY = "search_itinerary"
    const val API_CREATE_ITINERARY = "create_itinerary"
    const val API_POPULAR_ITINERARY="popular_itinerary_created"
    const val API_AUTOMATIC_ITINERARY="automatic_itinerary_created"
    const val API_CREATE_FAVOURITE_ITINERARY = "create_favourite_itinerary"
    const val API_REMOVE_FAVOURITE_ITINERARY = "remove_all_favourite_itinerary"
    const val API_UNFAVOURITE_SITE_FROM_FAVOURITE = "remove_favourite_itinerary"
    const val API_GET_ITINERARY = "get_itinerary"
    const val API_GET_HERITAGE_FILTER_CATEGORY_LIST = "getheritagefiltercategorylist"
    const val API_GET_TOURISM_PACKAGES = "get_tourism_packages"
    const val API_GET_RELATED_LINK = "related_link"
    const val API_SAVE_USER_PROFILE = "saveuserprofile"
    const val API_ADD_RATING = "add_rating"
    const val API_FAQ = "faq"
    const val API_ADD_FEEDBACK = "addfeedback"
    const val API_GET_AR_DIRECTIONS_SUPERIMPOSING_SITES = "get_ardirections_superimposing_sites"
    const val API_GET_HERITAGE_SITES = "getheritagelistwithcategory"
    const val API_EDIT_ITINERARY = "edit_itinerary"
    const val API_EDIT_FAVOURITE_ITINERARY = "edit_favourite_itinerary"
    const val API_GET_HELPLINE = "get_helpline"
    const val API_GET_QUIZ = "get_quiz"
    const val API_FOOTFALL_MAPPING = "addFootfall"
    const val API_SHARE_DETAILS = "add_social_media_details"
    const val API_AHM_UPDATES_FB =
        "https://graph.facebook.com/v10.0/me/feed?fields=comments.limit(1).summary(true)%2Clikes.limit(1).summary(true)%2Cid%2Cattachments&access_token=EAAMWfVZC8t6IBAFa8xiWKBnrZBWpt09ZCLfd826IbSKL7Ty6awSfFywgFyVN0KDDZASsVZChMzcOqaZB6dZBJuv2nF262RP7Pep8PdNyVYpUlyA08454T8Bet8zaannpvoW1xfZCJrmxFXOZBvegBspSPIryRbL6NJT83X42Q77AIR41aw7IFZC40vHlwEMY6E3MoZD"
}
enum class APIURL {
    DEVELOPER, PRODUCTION
}
