package com.example.haritagedemo.API

import android.content.Context
import android.content.SharedPreferences

class PreferanceManager(mContext:Context) {

    private val preferences: SharedPreferences
    private val PRIVATE_MODE = 0

    var context: Context

    init {
        preferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        context = mContext
    }

    fun clearPrefrences() {
        preferences.edit().clear().apply()
    }

    fun removePreference(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun setStringPreference(key: String, stringValue: String) {
        preferences.edit().putString(key, stringValue).apply()
    }

    fun setProminentValue(key: String,isDialogShow:Boolean) {
        preferences.edit().putBoolean(key,isDialogShow).apply()
    }


    fun setBooleanPreference(key: String, booleanValue: Boolean) {
        preferences.edit().putBoolean(key, booleanValue).apply()
    }

    fun setIntPreference(key: String, intValue: Int) {
        preferences.edit().putInt(key, intValue).apply()
    }

    fun getStringPreference(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getBooleanPreference(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getIntPreference(key: String): Int {
        return preferences.getInt(key, -1)
    }

    fun getProminentValue(key: String): Boolean {
        return preferences.getBoolean(key,false);
    }

    /**
     * get user login or not
     */
    fun getUserLogin(): Boolean {
        return preferences.getBoolean(IS_USER_ALREADY_LOGIN, false)
    }

    fun getGeofencesAdded(): Boolean {
        return preferences.getBoolean(IS_AHA_GEOFENCES_ADDED, false)
    }

    fun setGeofencesAdded(added: Boolean) {
        preferences.edit().putBoolean(IS_AHA_GEOFENCES_ADDED, added).apply()
    }

    /**
     * get user login or not
     */
    fun setUserLogin(isLogin: Boolean) {
        preferences.edit().putBoolean(IS_USER_ALREADY_LOGIN, isLogin).apply()
    }

    /**
     * get user login or not
     */
    fun getPreHome(): Boolean {
        return preferences.getBoolean(IS_PREMOHOME, false)
    }

    /**
     * get user login or not
     */
    fun setPreHome(isLogin: Boolean) {
        preferences.edit().putBoolean(IS_PREMOHOME, isLogin).apply()
    }




    /**
     * set shouldNeverAskForBackgroundLocation
     */
    fun setShouldNeverAskForBackgroundLocation(shouldAsk: Boolean) {
        setBooleanPreference(BACKGROUND_LOCATION, shouldAsk)
    }

    /**
     * get shouldNeverAskForBackgroundLocation
     */
    fun getShouldNeverAskForBackgroundLocation(): Boolean {
        return getBooleanPreference(BACKGROUND_LOCATION)
    }


    fun getUserId(): String? {
        return preferences.getString(APPUSERID, "unknown@amc.com")
    }

    fun setUserId(userId: String) {
        preferences.edit().putString(APPUSERID, userId).apply()
    }

    companion object {
        const val PREF_NAME = "amcheritage"
        const val HEADER = "HEADER"
        var FCM_TOKEN = "FCM_TOKEN"
        val NOTIFICATION_ID = "NOTIFICATION_ID"
        val IS_USER_ALREADY_LOGIN = "IS_USER_ALREADY_LOGIN"
        val IS_AHA_GEOFENCES_ADDED = "IS_AHA_GEOFENCES_ADDED"
        val USER_LOGIN_DATA = "USER_LOGIN_DATA"
        val LANGUAGE = "LANGUAGE"
        val BACKGROUND_LOCATION = "BACKGROUND_LOCATION"
        val APPUSERID = "APPUSERID"
        val USER_TYPE = "USER_TYPE"
        val RESENT_SEARCH = "RESENT_SEARCH"
        val IS_PREMOHOME = "IS_PREMOHOME"
        const val IS_PROMENENT = "IS_PROMENENT"
    }

}