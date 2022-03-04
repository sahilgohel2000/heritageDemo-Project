package com.example.haritagedemo.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.haritagedemo.Login.LoginActivity
import com.example.haritagedemo.MainActivity
import com.example.haritagedemo.R
import com.example.haritagedemo.loading
import com.example.haritagedemo.preHome.preHomeActivity
import com.example.haritagedemo.signupactivity

class Preloginsignup : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("preloginsignup","oncreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preloginsignup)

        val prelogin:Button=findViewById(R.id.prelogin)
        val presignup:Button=findViewById(R.id.presignup)

        val loading = loading(this)

        val ppolicy:TextView=findViewById(R.id.privacy)
        val skip:TextView=findViewById(R.id.skip)

        prelogin.setOnClickListener(View.OnClickListener {
            Log.d("preloginsignup","prelogin")
            val intentus:Intent= Intent(this, LoginActivity::class.java)
            startActivity(intentus)
        })

        presignup.setOnClickListener(View.OnClickListener {
            Log.d("preloginsignup","presignup")
            Toast.makeText(this,"signup",Toast.LENGTH_SHORT).show()
            val intent:Intent= Intent(this, signupactivity::class.java)
            startActivity(intent)
        })
        Log.d("Preloginsignup","ppolicy")

        ppolicy.setOnClickListener(
            View.OnClickListener {
                Toast.makeText(this,"privacy policy",Toast.LENGTH_LONG).show()
            })

        Log.d("Preloginsignup","skipbtn")

        skip.setOnClickListener(View.OnClickListener {
            Log.d("Preloginsignup","skipbtn")
//            val intent:Intent= Intent(this, MainActivity::class.java)
//            startActivity(intent)

            loading.loadingAlertDialog()
            val handler = Handler()
            handler.postDelayed(Runnable {
                loading.dismissDialog()
            },5000)

            val newIntent:Intent= Intent(this,preHomeActivity::class.java)
            startActivity(newIntent)
        })
    }
//
//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flfragment,fragment).commit()
//        }
}