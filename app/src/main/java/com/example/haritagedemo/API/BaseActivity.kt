package com.example.haritagedemo.API

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import com.example.haritagedemo.FieldNearbySitesLocation
import com.example.haritagedemo.SiteNearbyAdapter
import java.util.ArrayList

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mPreferanceManager: PreferanceManager
    internal var mContext:Context = this
    open var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPreferanceManager = PreferanceManager(mContext)
    }

    val secureKey: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(mContext.contentResolver, Settings.Secure.ANDROID_ID)

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        bindViews()
    }

    protected abstract fun bindViews()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

}