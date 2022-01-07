package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haritagedemo.R

class FestivalDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festival_detail)
    }
companion object{
    const val PICTURE_REQUEST = 100
    fun startActivity(mContext: Context, dataId: String?) {
        val intent = Intent(mContext, FestivalDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TITLE, dataId)
        mContext.startActivity(intent)
    }
}
}