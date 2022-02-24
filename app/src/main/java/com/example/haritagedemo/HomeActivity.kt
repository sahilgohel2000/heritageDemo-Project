package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)

        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.homeActivity,HomeFragment())
        fragmentTransaction.commit()
    }
    companion object {
        fun startActivity(mContext: Context) {
            val intent = Intent(mContext, HomeActivity::class.java)
            mContext.startActivity(intent)
        }
    }
}