package com.example.haritagedemo

import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_bottomsheet_quickview.*
import java.lang.Exception
import com.bumptech.glide.load.engine.DiskCacheStrategy





class HomeFragment : BaseFragment() {

    lateinit var bottomSheetLayout: LinearLayout
    lateinit var sheetBehaviorUnit: BottomSheetBehavior<*>
    private var mHeritageSiteDetailModel: HeritageSiteDetailModel?=null

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
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Handler(Looper.getMainLooper()).post { webView.loadUrl("javascript:deselectSite()") }
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setsUpMap() {

        webView.addJavascriptInterface(WebInterface(), "appInterface")
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

    inner class WebInterface(){
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
                        else {
                            Log.e("MainFragment","Else Part")
                        }
                    }

                })
        }
    }

    private fun callAPIHeritageSiteDetails(dataId: String?){

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

                    Log.d("response-->",response.result.toString())

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
                            mHeritageSiteDetailModel!!.amenities
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
        amenities: ArrayList<String>
    ) {
        Glide.with(mContext!!).load(fieldUploadUrl).into(course)

//        idTVCourseTracks.text = stripHtml(description) //stripHtml that removes Html tag and get data without tag
//        idTVCourseTracks.text = description
        idTVCourseName.text = heritageSiteName

        bottomSheetLayout.setOnClickListener(View.OnClickListener {
//            try {
//                val intent=Intent(mContext,EmptyActivity::class.java)
//                startActivity(intent)
//            }catch (e:Exception){
//                e.printStackTrace()
//            }

            if (type!=null){
                sheetBehaviorUnit.state =BottomSheetBehavior.STATE_COLLAPSED
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