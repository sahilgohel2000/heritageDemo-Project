package com.example.haritagedemo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.spinner_layout.*
import android.widget.AdapterView.OnItemSelectedListener as AdapterViewOnItemSelectedListener
import android.widget.AdapterView

import android.widget.Toast

import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.haritagedemo.RoomDatabase.UserDatabase
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jaredrummler.materialspinner.MaterialSpinner
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var searchButton : ImageView
    private lateinit var notificationButton : ImageView

    private lateinit var Homebutton:ConstraintLayout
    private lateinit var FAQ:ConstraintLayout

    private lateinit var Tname:TextView
    private lateinit var Temail:TextView

    private lateinit var mAuth: FirebaseAuth

    //Constraint Layout
    private lateinit var ProfileInfo:ConstraintLayout

    //second WebView
    private lateinit var secondwebView:WebView
    private lateinit var progressDialog: ProgressDialog

    private lateinit var activity: Activity

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val fragmentTransaction=supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.firstPage,HomeFragment())
            fragmentTransaction.commit()
        }catch (e:Exception){
            e.printStackTrace()
        }


//        secondwebView = findViewById(R.id.secondWebView)
//        setUpMapViews()

//        Handler().postDelayed({
//            progressDialog.dismiss()
//        },2000)

        navigationView = findViewById(R.id.navigation)

        searchButton = findViewById(R.id.search)
        notificationButton = findViewById(R.id.notification)

        searchButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"search",Toast.LENGTH_LONG).show()
            val searchIntent:Intent = Intent(this,SearchActivity::class.java)
            startActivity(searchIntent)
        })

        notificationButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Notification",Toast.LENGTH_LONG).show()
            val notificationIntent:Intent = Intent(this,NotificationActivity::class.java)
            startActivity(notificationIntent)
        })

        val Fspinner:MaterialSpinner = findViewById(R.id.spin)
        Fspinner.setItems("Haritage Walks","Morning Haritage Walks","Evening Hariage Walks")
        Fspinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val Sspinner:MaterialSpinner = findViewById(R.id.spin2)
        Sspinner.setItems("Tourism Package","AMC Tourism Packages")
        Sspinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        Homebutton = findViewById(R.id.secondLayout)
        Homebutton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Home Activity",Toast.LENGTH_SHORT).show()
            val HomeIntent:Intent = Intent(this,HomeActivity::class.java)
            startActivity(HomeIntent)
        })

        FAQ = findViewById(R.id.twelve)
        FAQ.setOnClickListener(View.OnClickListener {
            val FAQIntent:Intent = Intent(this,FAQActivity::class.java)
            startActivity(FAQIntent)
        })

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        //if login with room database
        Temail = findViewById(R.id.profileName)
        var ename: String? = intent.getStringExtra("name")
        Temail.setText(ename)

        //if login with google
        Tname = findViewById(R.id.homeText)
        Tname.text = currentUser?.displayName

        ProfileInfo = findViewById(R.id.firstLayout)
        ProfileInfo.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogTitle)

            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(R.drawable.ic_baseline_info)

            //performing logout
            builder.setPositiveButton("Logout"){dialogInterface, which ->
                Toast.makeText(applicationContext,"Logout",Toast.LENGTH_LONG).show()
                mAuth.signOut()
                Temail.setText("Welcome to AHA Ahmedabad")
                Tname.setText("Welcome to AHA Ahmedbad")
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
            }
            val alertDialog:AlertDialog = builder.create()
            alertDialog.cancel()
            alertDialog.show()
        })
        setupviews()
    }


    private fun setupviews() {
        setUpDrawerLayout()
        Log.d("MainActivity", "seupview")
    }

    private fun setUpDrawerLayout() {
        Log.d("MainActivity", "setUpDrawerLayout")

        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, maindrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "onOptionsItemSelected")

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpMapViews() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("wait for loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        secondwebView.settings.javaScriptEnabled = true

        secondwebView.settings.allowContentAccess = true
        secondwebView.settings.allowFileAccess = true
        secondwebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        secondwebView.loadUrl(
            "file:///android_asset/index.html"
        )

        secondwebView.webChromeClient = object : WebChromeClient(){
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        secondwebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
    }
}
