package com.example.haritagedemo

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
}