package com.example.haritagedemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.getLocation
import com.example.haritagedemo.Model.EventDetailModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_bottomsheet_quickview.*
import com.example.haritagedemo.Model.FestivalDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.example.haritagedemo.API.Util.getDistance
import com.example.haritagedemo.Activity.HeritageSiteDetailActivity.Companion.RQ_LOCATION
import com.example.haritagedemo.Util.LocationHelper
import org.json.JSONObject

class HomeFragment : BaseFragment(),LocationHelper.LocationHelperCallback {

    lateinit var bottomSheetLayout: LinearLayout
    private val RC_BACKGROUND_LOCATION = 2021
    lateinit var sheetBehaviorUnit: BottomSheetBehavior<*>
    private var mHeritageSiteDetailModel: HeritageSiteDetailModel?=null
    private var latLng: LatLng? = null
    private var isPermissionFor = Const.PERMISSION.TAKE_PHOTO
    private var mFestivalDetailModel: FestivalDetailModel? = null
    private var mEventDetailModel: EventDetailModel? = null
    private var mLocalCuisineDetail: LocalCuisineDetail? = null
    private var locationHelper: LocationHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindViews(view: View) {
        setsUpMap()

        setLoc.setOnClickListener {
            Log.d("HomeFragmnet","setLocation Btn")
            setCurrentLocation()
            checkLocationPermission()
        }
        bottomSheetLayout = view.findViewById(R.id.bottomsheetLayout)
        sheetBehaviorUnit = BottomSheetBehavior.from(bottomSheetLayout)
        sheetBehaviorUnit.state = BottomSheetBehavior.STATE_HIDDEN

        sheetBehaviorUnit.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState!=null) {
                    if (sheetBehaviorUnit.state == BottomSheetBehavior.STATE_HIDDEN){
                        Handler(Looper.getMainLooper()).post {
                            webView.loadUrl("javascript:deselectSite()")
                            //sheetBehaviorUnit.state = BottomSheetBehavior.STATE_HIDDEN
                        }
                    }
                    else{}
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setsUpMap() {

        webView.addJavascriptInterface(WebInterface(mContext!!), "appInterface")
        webView.settings.javaScriptEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.loadUrl(
            "file:///android_asset/index.html"
        )

        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (activity != null){
                    Handler().postDelayed(Runnable {
                        val intent = requireActivity().intent.extras?.getParcelable<Intent>("filterData")
                        if (intent == null) setCurrentLocation()
                        else Log.d("HomeFragmnet","Else Not Work")
                    },4000)
                }
//                super.onPageFinished(view, url)
            }
        }
    }

    fun setCurrentLocation(){
        Log.d("HomeFragmnet","set Current Location 1")
            latLng = LatLng(23.0477,72.5728)

        //latitude=23.0477, longitude=72.5728
        if (latLng != null){
            Log.d("HomeFragmnet","setCurrentLocation If")
            try {
                Log.d("HomeFragmnet","setCurrentLocation Try")
                val jsonObject = JSONObject().apply {
                    this.put("latitude",latLng!!.latitude)
                    this.put("longitude",latLng!!.longitude)
                    this.put("icon",resources.getString(R.string.gis_map_current_location_icon))
                }
                webView?.loadUrl("javascript:setMyLocation('$jsonObject')")
                Log.d("HomeFragmnet","setCurrentLocation")
            }catch (e:Exception){
                e.printStackTrace()
                Log.d("HomeFragmnet","setCurrentLocation catch="+e.printStackTrace().toString())
            }
        }else{
            Log.d("HomeFragmnet","Else Latlng is Null")
        }
    }

//    fun getLocation(){
//        Log.d("HomeFragmnet","Get Location Function")
//
//        if (checkLocationPermission()){
//            Log.d("HomeFragmnet","If CheckLocationPermission")
//
//            locationHelper = LocationHelper(activity,this,true,false,false)
//        }
//        else{
//            requestPermission()
//            Log.d("HomeFragmnet","request Permission")
//
//        }
//    }

    private fun requestPermission() {
        Log.d("HomeFragmnet","request Permission :2")

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            RQ_LOCATION
        )
    }

    fun checkLocationPermission():Boolean{
        Log.d("HomeFragmnet","Check Location Permission 2")

        val result=ContextCompat.checkSelfPermission(mContext!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
        Log.d("HomeFragmnet","Check Location Permission 3")
        return result == PackageManager.PERMISSION_GRANTED
    }

    inner class WebInterface(val mContext: Context){
        @JavascriptInterface
        fun getPinInfo(dataId: String?, type: String?) {
                activity!!.runOnUiThread(Runnable {
                    Log.d("MainActivity","${dataId}")
                    Log.d("MainActivity","${type}")

                    //idTVCourseName.text = dataId.toString()
                    //idTVCourseDuration.text = type.toString()
                    sheetBehaviorUnit.state = BottomSheetBehavior.STATE_COLLAPSED

                    if (!dataId.isNullOrEmpty() && type != null){
                        if (type.equals(Const.HERITAGETYPE.HERITAGE_SITE.toString(),true)){
                            callAPIHeritageSiteDetails(dataId)
                        }
                        else if (type.equals(Const.HERITAGETYPE.FESTIVALS.toString(),true)){
                            callAPIGetFestivalDetails(dataId)
                        }
                        else if (type.equals(Const.HERITAGETYPE.EVENTS_ENTERTAINMENT.toString(),true)){
                            callAPIEventDetail(dataId)
                        }
                        else if (type.equals(Const.HERITAGETYPE.LOCAL_CUISINE.toString(),true)){
                            callAPILocalCusine(dataId)
                        }
                        else {}
                    }
                })
        }
    }

    private fun callAPILocalCusine(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferenceManager.getLanguage()?.code

        serviceManager.apiLocalCuisineDetail(
            hashMap,
            object : ResponseListener<Response<LocalCuisineDetail>>(){
                override fun onRequestSuccess(response: Response<LocalCuisineDetail>) {
                    mLocalCuisineDetail = response.result

                    activity!!.runOnUiThread(Runnable {
                        setupBottomCusine(
                        mLocalCuisineDetail!!.heritageSiteName,
                        mLocalCuisineDetail!!.fieldUploadUrl.get(0),
                        mLocalCuisineDetail!!.fieldHeritageSitesCategory.joinToString(
                        separator = " "
                        ),
                        mLocalCuisineDetail!!.type,
                        response.result!!.nid
                        )
                    })
                }

                override fun onRequestSuccess(
                    isSuccess: Boolean,
                    response: Response<LocalCuisineDetail>,
                    message: String
                ) {
                    super.onRequestSuccess(isSuccess, response, message)
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

    private fun setupBottomCusine(
        heritageSiteName: String,
        get: String,
        joinToString: String,
        type: String,
        nid: String
    ) {
        Glide.with(mContext!!).load(get).into(course)

        idTVCourseName.text = heritageSiteName
        idTVCourseTracks.text = joinToString

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,nid)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        idBtnDismiss.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mLocalCuisineDetail!!.latitude, mLocalCuisineDetail!!.longitude)

            Util.showGetThereDialog(
                mContext!!,
                mLocalCuisineDetail!!.translateName,
                mLocalCuisineDetail!!.latitude,
                mLocalCuisineDetail!!.longitude,
                latLng
            )
        }
    }

    private fun callAPIGetFestivalDetails(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferenceManager.getLanguage()?.code

        serviceManager.apiGetFestivalDetails(
            hashMap,
            object:ResponseListener<Response<FestivalDetailModel>>(){
                override fun onRequestSuccess(response: Response<FestivalDetailModel>) {
                    mFestivalDetailModel = response.result

                    activity!!.runOnUiThread(Runnable {
                    setupBottomSites(
                        mFestivalDetailModel!!.heritageSiteName,
                        mFestivalDetailModel!!.fieldUploadUrl.get(0),
                        mFestivalDetailModel!!.fieldFestivalCategory.joinToString(
                            separator = " "
                        ),
                        mFestivalDetailModel!!.heritageSiteName.toString(),
                        mFestivalDetailModel!!.type,
                        response.result!!.nid
                    )
                    })
                }

                override fun onRequestSuccess(
                    isSuccess: Boolean,
                    response: Response<FestivalDetailModel>,
                    message: String
                ) {
                    super.onRequestSuccess(isSuccess, response, message)
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

    private fun setupBottomSites(
        heritageSiteName: String,
        get: String,
        joinToString: String,
        toString: String,
        type: String,
        nid: String
    ) {
        Glide.with(mContext!!).load(get).into(course)

        idTVCourseName.text = heritageSiteName
        idTVCourseTracks.text = joinToString

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,nid)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        idBtnDismiss.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mFestivalDetailModel!!.latitude, mFestivalDetailModel!!.longitude)

            Util.showGetThereDialog(
                mContext!!,
                mFestivalDetailModel!!.heritageSiteName,
                mFestivalDetailModel!!.latitude,
                mFestivalDetailModel!!.longitude,
                latLng
            )
        }
    }

    //CALL API EVENT DETAIL
    private fun callAPIEventDetail(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferenceManager.getLanguage()?.code

        serviceManager.apiEventDetail(
            hashMap,
            object : ResponseListener<Response<EventDetailModel>>(){
                override fun onRequestSuccess(response: Response<EventDetailModel>) {
                    mEventDetailModel = response.result

                    activity!!.runOnUiThread(Runnable {
                    setupBottomSiteEvent(
                        mEventDetailModel!!.heritageSiteName,
                        mEventDetailModel!!.fieldHeritageSitesCategory.joinToString(
                            separator = " "
                        ),
                        mEventDetailModel!!.fieldUploadUrl.get(0),
                        mEventDetailModel!!.type,
                        response.result!!.nid
                    )
                    })
                }

                override fun onRequestSuccess(
                    isSuccess: Boolean,
                    response: Response<EventDetailModel>,
                    message: String
                ) {
                    super.onRequestSuccess(isSuccess, response, message)
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

    private fun setupBottomSiteEvent(
        heritageSiteName: String,
        joinToString: String,
        get: String,
        type: String,
        nid: String
    ) {
        Glide.with(mContext!!).load(get).into(course)

//        idTVCourseTracks.text = stripHtml(description) //stripHtml that removes Html tag and get data without tag
        idTVCourseName.text = heritageSiteName
        idTVCourseTracks.text = joinToString

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,nid)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        idBtnDismiss.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mEventDetailModel!!.latitude, mEventDetailModel!!.longitude)

            Util.showGetThereDialog(
                mContext!!,
                mEventDetailModel!!.heritageSiteName,
                mEventDetailModel!!.latitude,
                mEventDetailModel!!.longitude,
                latLng
            )
        }
    }

    //CALL API HERITAGE SITE
     fun callAPIHeritageSiteDetails(dataId: String?){

        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferenceManager.getLanguage()?.code

        serviceManager.apiHeritageSiteDetails(
            hashMap,
            object : ResponseListener<Response<HeritageSiteDetailModel>>() {
                override fun onRequestSuccess(response: Response<HeritageSiteDetailModel>) {
                    mHeritageSiteDetailModel = response.result
                    Log.d("HomeFragmnet","Response:1 =->:"+mHeritageSiteDetailModel!!.latitude.toString()+mHeritageSiteDetailModel!!.longitude.toString())

                    activity!!.runOnUiThread(Runnable {
                        setupBottomSite(
                            dataId,
                            mHeritageSiteDetailModel!!.heritageSiteName,
                            mHeritageSiteDetailModel!!.description,
                            mHeritageSiteDetailModel!!.fieldHeritageSitesCategory.joinToString(
                                separator = ""
                            ),
                            mHeritageSiteDetailModel!!.type,
                            response.result!!.nid,
                            mHeritageSiteDetailModel!!.fieldUploadUrl.get(0),
                            mHeritageSiteDetailModel!!.amenities,
                            getLocation(
                                mHeritageSiteDetailModel!!.latitude,
                                mHeritageSiteDetailModel!!.longitude
                            ),
                            mHeritageSiteDetailModel!!.latitude,
                            mHeritageSiteDetailModel!!.longitude
                        )
                    })
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

    private fun setupBottomSite(
        dataId: String?,
        heritageSiteName: String,
        description: String,
        joinToString: String,
        type: String,
        nid: String,
        fieldUploadUrl: String,
        amenities: ArrayList<String>,
        location: Location,
        latitude: Double?,
        longitude: Double?
    ) {
        Glide.with(mContext!!).load(fieldUploadUrl).into(course)

    //    idTVCourseTracks.text = stripHtml(description) //stripHtml that removes Html tag and get data without tag
        idTVCourseName.text = heritageSiteName
        idTVCourseTracks.text = joinToString

        latLng = LatLng(23.0477,72.5728)

        idTVCourseDuration.text = location.toString()
//        latLng = LatLng(latitude!!,longitude!!)
        Log.d("HomeFragmnet","set latLng"+latLng+latitude.toString()+longitude.toString())


        val distance = if (latLng != null) {
            Log.d("HomeFragmnet","check Distance 1"+location.toString())
            Log.d("HomeFragmnet","check Distance 3"+latitude.toString()+longitude.toString())
// latitude=23.0477, longitude=72.5728
// 23.06054           72.5802878
            getDistance(
                location,
                getLocation(latLng!!.latitude, latLng!!.longitude)
            )
            Log.d("HomeFragmnet","check Distance 2"+ getDistance(location, Util.getLocation(latLng!!.latitude, latLng!!.longitude)).toString())

        } else{
            Log.d("HomeFragmnet","Its Not Working")
        }

        Log.d("HomeFragmnet","Its Not Working"+distance.toString())

        idTVCourseDuration.text = getString(R.string.lbl_approximate_distance,getDistance(location, Util.getLocation(latLng!!.latitude, latLng!!.longitude)).toString())

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,dataId)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        idBtnDismiss.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mHeritageSiteDetailModel!!.latitude, mHeritageSiteDetailModel!!.longitude)

            Util.showGetThereDialog(
                mContext!!,
                mHeritageSiteDetailModel!!.translateName,
                mHeritageSiteDetailModel!!.latitude,
                mHeritageSiteDetailModel!!.longitude,
                latLng
            )
        }
    }

    //this stripHtml method removes the Html Tag without this we can get data with html tag
    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onLocationFound(location: Location?) {
        Log.d("HomeFragmnet","onLocation Found 1"+location.toString())

        if (location!=null){
            Log.d("HomeFragmnet","loc!=null")

            latLng = LatLng(location.latitude, location.longitude)
            setCurrentLocation()
            checkLocationPermission()
        }
        Log.d("HomeFragmnet","onlocation found!!"+latLng.toString())

    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        Log.d("HomeFragmnet","on Request Permission")
//
//        if (locationHelper != null){
//            Log.d("HomeFragmnet","If Locationhelper != null")
//
//            locationHelper?.onRequestPermissionsResult(requestCode,permissions,grantResults)
//        }
//        when(requestCode){
//            RQ_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Log.d("HomeFragmnet","Permision granted + getLocation")
//
//                getLocation()
//            }else{
//                Log.d("HomeFragmnet","its not works")
//            }
//            RC_BACKGROUND_LOCATION -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Log.d("HomeFragmnet","If EC_BACKGROUND_LOCATION")
//
//                }else{
//                    Log.d("HomeFragmnet","Else RC_BACK_LOC")
//
//                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
//                        Log.d("HomeFragmnet","Else, If RC_BACK_LOC")
//
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                            Uri.fromParts(
//                                "package",
//                                requireActivity().packageName,
//                                null
//                            )
//                            )
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                    }else{
//                        Log.d("HomeFragmnet","Issue in Permission")
//                    }
//                }
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

    override fun onDeclineProcessForLocation() {
        Log.d("HomeFragmnet","onDeclineProcessForLocation")
    }

    companion object {
        const val RQ_LOCATION = 1
        const val shouldChangeLikeButton: Boolean = false
        const val LIKE_BTN_CHANGE_REQ = 27
        const val IS_LIKED_IN_SITE_DETAIL = "isLikedInSiteDetail";
        const val SITE_ID = "siteId";

        fun getAppNameFromPkgName(packagename: String, context: Context): String {
            return try {
                val packageManager = context.packageManager
                val info: ApplicationInfo =
                    packageManager.getApplicationInfo(packagename, PackageManager.GET_META_DATA)
                packageManager.getApplicationLabel(info) as String
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                ""
            }
        }

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}