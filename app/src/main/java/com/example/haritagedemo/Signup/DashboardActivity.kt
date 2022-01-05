package com.example.haritagedemo.Signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.haritagedemo.R
import com.example.haritagedemo.SplashScreen.Preloginsignup
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    lateinit var id_txt : TextView
    lateinit var name_txt : TextView
    lateinit var email_txt : TextView
    lateinit var img_view : ImageView
    lateinit var Signout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        id_txt = findViewById(R.id.id_txt)
        name_txt = findViewById(R.id.name_txt)
        email_txt = findViewById(R.id.email_txt)
        img_view = findViewById(R.id.profile_image)
        Signout = findViewById(R.id.sign_out_btn)

        id_txt.text = currentUser?.uid
        name_txt.text = currentUser?.displayName
        email_txt.text = currentUser?.email

        Glide.with(this).load(currentUser?.photoUrl).into(img_view);

        Signout.setOnClickListener(View.OnClickListener {
            mAuth.signOut()
            val intent: Intent = Intent(this, Preloginsignup::class.java)
            startActivity(intent)
            finish()
        })

    }
}