package com.example.haritagedemo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.haritagedemo.API.Const
import com.example.haritagedemo.API.Response
import com.example.haritagedemo.API.ResponseListener
import com.example.haritagedemo.API.ServiceManager
import com.example.haritagedemo.Activity.*
import com.example.haritagedemo.Model.RelatedLinkModel
import com.example.haritagedemo.RoomDatabase.UserDatabase
import com.example.haritagedemo.preHome.preHomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_related_link.*
import java.lang.Exception


class MainActivity : AppCompatActivity(),RelatedLinkAdapter.CallBack {

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

    private var jAdapterRelated:RelatedLinkAdapter? = null
    private val jArrayList:ArrayList<RelatedLinkModel?> = ArrayList()
    private var jLayoutManager: LinearLayoutManager? = null

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeCallApi()
        jAdapterRelated = RelatedLinkAdapter(this,jArrayList,this)
        jLayoutManager = LinearLayoutManager(this)
        with(linkNames){
            layoutManager = jLayoutManager
            adapter = jAdapterRelated
        }


        try {
            val fragmentTransaction=supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.firstPage,HomeFragment())
            fragmentTransaction.commit()
        }catch (e:Exception){
            e.printStackTrace()
        }

        navigationView = findViewById(R.id.navigation)

        searchButton = findViewById(R.id.search)
        notificationButton = findViewById(R.id.notification)

        searchButton.setOnClickListener(View.OnClickListener {
            val searchIntent:Intent = Intent(this,SearchActivity::class.java)
            startActivity(searchIntent)
        })

        ChooseLanguage.setOnClickListener(View.OnClickListener {
            val languageIntent:Intent = Intent(this,chooseLanguage::class.java)
            startActivity(languageIntent)
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

            if (position == 1){
                HeritageWalkActivity.startActivity(this,Const.HERITAGEWALK.MORNING)
            }
            if (position == 2){
                HeritageWalkActivity.startActivity(this,Const.HERITAGEWALK.EVENING)
            }
        }

        val Sspinner:MaterialSpinner = findViewById(R.id.TourismSpinner)
        Sspinner.setItems("Tourism Package","AMC Tourism Packages")
        Sspinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
            val TourismIntent:Intent = Intent(this,TourismPackageActivity::class.java)
            startActivity(TourismIntent)
        }

        Homebutton = findViewById(R.id.secondLayout)
        Homebutton.setOnClickListener(View.OnClickListener {
            val homeIntent:Intent = Intent(this,preHomeActivity::class.java)
            startActivity(homeIntent)
        })

        helpLine.setOnClickListener(View.OnClickListener {
            val helpline:Intent = Intent(this,HelpActivity::class.java)
            startActivity(helpline)
        })

        FAQ = findViewById(R.id.twelve)
        FAQ.setOnClickListener(View.OnClickListener {
            val FAQIntent:Intent = Intent(this,FAQActivity::class.java)
            startActivity(FAQIntent)
        })

        AgelessAhmedbad.setOnClickListener(View.OnClickListener {
            val aboutAhmedabadIntent = Intent(this,AboutAhmedabadActivity::class.java)
            startActivity(aboutAhmedabadIntent)
        })

        heritageQuiz.setOnClickListener(View.OnClickListener {
            val heritagesiteQuiz=Intent(this,HeritageQuizActivity::class.java)
            startActivity(heritagesiteQuiz)
        })

        privayPolicy.setOnClickListener(View.OnClickListener {
            val privacypolicyIntent:Intent = Intent(this,Privacy_policyActivity::class.java)
            startActivity(privacypolicyIntent)
        })

        newsFeed.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(this,relatedLinkActivity::class.java)
            startActivity(newIntent)
        })

        third.setOnClickListener(View.OnClickListener {
            val thirdIntent = Intent(this,IntroActivity::class.java)
            startActivity(thirdIntent)
        })

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        //if login with room database
        Temail = findViewById(R.id.profileName)
        var ename: String? = null
        ename = intent.getStringExtra("name")
        if (ename != null){
            Temail.setText(ename)
            LogOutBtn.visibility = View.VISIBLE
        }else{
            Temail.setText("Welcome to AHA Ahmedabad")
            LogOutBtn.visibility = View.GONE
        }

        LogOutBtn.setOnClickListener(View.OnClickListener {
            alertBox()
        })

        //if login with google
        Tname = findViewById(R.id.homeText)
        Tname.text = currentUser?.displayName
        Glide.with(this).load(currentUser?.photoUrl).into(profileImage)
        if (Tname!=null){
            LogOutBtn.visibility = View.VISIBLE
        }
//        ProfileInfo = findViewById(R.id.firstLayout)
//        ProfileInfo.setOnClickListener(View.OnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle(R.string.dialogTitle)
//
//            builder.setMessage(R.string.dialogMessage)
//            builder.setIcon(R.drawable.ic_baseline_info)
//
//            //performing logout
//            builder.setPositiveButton("Logout"){dialogInterface, which ->
//                Toast.makeText(applicationContext,"Logout",Toast.LENGTH_LONG).show()
//                mAuth.signOut()
//                Temail.setText("Welcome to AHA Ahmedabad")
//                Tname.setText("Welcome to AHA Ahmedbad")
//            }
//            builder.setNegativeButton("No"){dialogInterface, which ->
//                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
//            }
//            val alertDialog:AlertDialog = builder.create()
//            alertDialog.cancel()
//            alertDialog.show()
//        })
        setupviews()
    }


    private fun alertBox() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)

        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(R.drawable.ic_baseline_info)

        //performing logout
        builder.setPositiveButton("Logout"){dialogInterface, which ->
            Toast.makeText(applicationContext,"Logout",Toast.LENGTH_LONG).show()
            mAuth.signOut()
            Temail.text = null
            Temail.setText("Welcome to AHA Ahmedabad")
            Tname.setText("Welcome to AHA Ahmedbad")
            LogOutBtn.visibility = View.GONE
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
        }
        val alertDialog:AlertDialog = builder.create()
        alertDialog.cancel()
        alertDialog.show()
    }

    private fun initializeCallApi() {
        val paramMap=HashMap<String,Any?>()

        ServiceManager(this).apiGetRelatedLink(
            paramMap,
            object : ResponseListener<retrofit2.Response<Response<ArrayList<RelatedLinkModel?>>>>(){
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<RelatedLinkModel?>>>) {
                    val responseBody= response.body()

                    if (responseBody != null && responseBody.code ==Const.SUCCESS){
                        setRelatedLink(responseBody)
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }

                override fun onRequestFailed(message: String) {
                    super.onRequestFailed(message)
                }
            }
        )
    }

    private fun setRelatedLink(responseBody: Response<java.util.ArrayList<RelatedLinkModel?>>) {
        jArrayList.addAll(responseBody.result!!)
        jAdapterRelated!!.notifyDataSetChanged()
    }

    private fun setupviews() {
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, maindrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

    override fun OnRelatedLink(pos: RelatedLinkModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pos.link))
        startActivity(intent)
    }
}
