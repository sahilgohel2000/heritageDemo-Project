package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haritagedemo.R

class LocalCuisineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_cuisine)
    }
    companion object{
        const val RQ_LOCATION = 1
        const val PICTURE_REQUEST = 100
        fun startActivity(mContext: Context, dataId: String?) {
            val intent = Intent(mContext, LocalCuisineActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE, dataId)
            mContext.startActivity(intent)
        }
    }
}