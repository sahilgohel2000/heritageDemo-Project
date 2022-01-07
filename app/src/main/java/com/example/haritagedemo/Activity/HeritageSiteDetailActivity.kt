package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haritagedemo.R

class HeritageSiteDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heritage_site_detail)
    }
    companion object{
        const val RQ_LOCATION = 1
        const val PICTURE_REQUEST = 100
        const val SITE_DETAIL = 101

        fun startActivity(mContext: Context,dataId:String?)
        {
            val intent= Intent(mContext, HeritageSiteDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE,dataId)
            mContext.startActivity(intent)
        }
        fun getIntent(mContext: Context,dataId:String?):Intent{
            return Intent(mContext, HeritageSiteDetailActivity::class.java).apply {
                putExtra(Intent.EXTRA_TITLE,dataId)
            }
        }
    }
}