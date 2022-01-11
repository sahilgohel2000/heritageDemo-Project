package com.example.haritagedemo

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.getLocation
import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_bottomsheet_quickview.*
import com.example.haritagedemo.Model.FestivalDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : BaseFragment() {

    lateinit var bottomSheetLayout: LinearLayout
    lateinit var sheetBehaviorUnit: BottomSheetBehavior<*>
    private var mHeritageSiteDetailModel: HeritageSiteDetailModel?=null
    private var latLng: LatLng? = null
    private var mFestivalDetailModel: FestivalDetailModel? = null
    private var mEventDetailModel: EventDetailModel? = null
    private var mLocalCuisineDetail: LocalCuisineDetail? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindViews(view: View) {
        setsUpMap()

        bottomSheetLayout = view.findViewById(R.id.bottomsheetLayout)
        sheetBehaviorUnit = BottomSheetBehavior.from(bottomSheetLayout)
        sheetBehaviorUnit.state = BottomSheetBehavior.STATE_HIDDEN

        sheetBehaviorUnit.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState!=null) {
                    if (sheetBehaviorUnit.state == BottomSheetBehavior.STATE_HIDDEN){
                        Handler(Looper.getMainLooper()).post {
                            webView.loadUrl("javascript:deselectSite()")
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
                super.onPageFinished(view, url)
            }
        }
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
                        else {
                            Log.e("MainFragment","Else Part")
                        }
                    }
                })
        }
    }

    private fun callAPILocalCusine(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

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
    }

    private fun callAPIGetFestivalDetails(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

        serviceManager.apiGetFestivalDetails(
            hashMap,
            object:ResponseListener<Response<FestivalDetailModel>>(){
                override fun onRequestSuccess(response: Response<FestivalDetailModel>) {
                    mFestivalDetailModel = response.result
                    Log.d("responses-->",response.result.toString())
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
    }

    //CALL API EVENT DETAIL
    private fun callAPIEventDetail(dataId: String?){
        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

        serviceManager.apiEventDetail(
            hashMap,
            object : ResponseListener<Response<EventDetailModel>>(){
                override fun onRequestSuccess(response: Response<EventDetailModel>) {
                    mEventDetailModel = response.result
                    Log.d("Events-->",response.result.toString())
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
//            try {
//                val intent=Intent(mContext,EmptyActivity::class.java)
//                startActivity(intent)
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,nid)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })
    }

    //CALL API HERITAGE SITE
     fun callAPIHeritageSiteDetails(dataId: String?){

        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

        serviceManager.apiHeritageSiteDetails(
            hashMap,
            object : ResponseListener<Response<HeritageSiteDetailModel>>() {
                override fun onRequestSuccess(response: Response<HeritageSiteDetailModel>) {
//                    super.onRequestSuccess(response)
                    mHeritageSiteDetailModel = response.result

                    //Log.d("response-->",response.result.toString())

                    activity!!.runOnUiThread(Runnable {
                        Log.d("HomeFragment", "HeritageName:"+mHeritageSiteDetailModel!!.heritageSiteName)
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
                            )
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
        location: Location
    ) {
        Glide.with(mContext!!).load(fieldUploadUrl).into(course)

//        idTVCourseTracks.text = stripHtml(description) //stripHtml that removes Html tag and get data without tag
        idTVCourseName.text = heritageSiteName
        idTVCourseTracks.text = joinToString

        Log.d("HomeFragment",location.toString())

        idTVCourseDuration.text = location.toString()

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
//            try {
//                val intent=Intent(mContext,EmptyActivity::class.java)
//                startActivity(intent)
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
            if (type!=null)
            {
                Util.openDetailsScreen(mContext!!,type,dataId)
                sheetBehaviorUnit.state=BottomSheetBehavior.STATE_COLLAPSED
            }
        })

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

}