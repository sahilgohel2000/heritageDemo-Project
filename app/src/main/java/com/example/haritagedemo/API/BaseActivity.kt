package com.example.haritagedemo.API

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mPreferenceManager: PreferanceManager
    internal var mContext: Context = this
    val TAG = this.javaClass.simpleName
    var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPreferenceManager= PreferanceManager(mContext)
    }
    override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
        bindViews()
    }

    protected abstract fun bindViews()
}