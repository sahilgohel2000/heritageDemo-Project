package com.example.haritagedemo.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.haritagedemo.Signup.DashboardActivity
import com.example.haritagedemo.R
import com.google.firebase.auth.FirebaseAuth

class Splashscreen : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        val splash : ConstraintLayout = findViewById( R.id.layout )
//
//        splash.alpha=0f
//
//        splash.animate().setDuration(6000).alpha(1f).withEndAction {
//
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//            finish()
//            val i:Intent= Intent(this,Preloginsignup::class.java)
//            startActivity(i)
//
//        }

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val h : Handler = Handler()

        h.postDelayed({
            if(user != null)
            {
                val dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            }
            else{
                val preloginIntent = Intent(this, Preloginsignup::class.java)
                startActivity(preloginIntent)
                finish()
            }
        },2000)
    }
}