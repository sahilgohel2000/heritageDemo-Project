package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.media.AudioMetadataReadMap
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_heritage_site_detail.*
import java.io.File
import java.util.ArrayList
import java.util.HashMap


class HeritageSiteDetailActivity :BaseActivity() {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private var mAdapterAmenities: AmentiesAdapter? = null
    private var mArrayListAmenities: ArrayList<String?> = ArrayList()

    private lateinit var mHeritageSiteDetailModel: HeritageSiteDetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heritage_site_detail)
    }

    override fun bindViews() {
        dataId = intent.getStringExtra(Intent.EXTRA_TITLE)
        contentMessage =intent.getStringExtra("message")
        titleMsg = intent.getStringExtra("title")
        type = intent.getStringExtra("type")

        callAPIHeritageSiteDetails()

        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        mAdapterAmenities =
            AmentiesAdapter(
                mContext,
                mArrayListAmenities
            )
        with(amentiesRecycler) {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal, spacingVertical))
            adapter = mAdapterAmenities
        }
    }

    private fun callAPIHeritageSiteDetails() {
        val serviceManager = ServiceManager(mContext)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

        serviceManager.apiHeritageSiteDetails(
            hashMap,
            object : ResponseListener<Response<HeritageSiteDetailModel>>(){
                override fun onRequestSuccess(response: Response<HeritageSiteDetailModel>) {
                    super.onRequestSuccess(response)
                    mHeritageSiteDetailModel = response.result!!
                    Log.d("response-->",response.result.toString())
                    setData()
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

    private fun setData() {
        heritage_title.text = mHeritageSiteDetailModel.heritageSiteName

        mCustomPagerAdapter=CustomPagerAdapter(
            mContext,
            mHeritageSiteDetailModel.fieldUploadUrl
        )
        mViewpager.adapter = mCustomPagerAdapter

        heritage_desc.text = stripHtml(mHeritageSiteDetailModel.description)

//        sCustomPagerAdapter=CustomPagerAdapter(
//            mContext,
//            mHeritageSiteDetailModel.amenities
//        )
//        mViewpagerfirst.adapter = sCustomPagerAdapter


        if (!mHeritageSiteDetailModel?.amenities!!.isNullOrEmpty()) {
            mArrayListAmenities.addAll(mHeritageSiteDetailModel?.amenities!!)
            mAdapterAmenities?.notifyDataSetChanged()
            amentiesRecycler.visibility = View.VISIBLE
            heritageAmenties.visibility = View.VISIBLE
        } else {
            amentiesRecycler.visibility = View.GONE
        }

    }

    //this stripHtml method removes the Html Tag without this we can get data with html tag
    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    companion object {
        const val RQ_LOCATION = 1
        const val PICTURE_REQUEST = 100
        const val SITE_DETAIL = 101

        fun startActivity(mContext: Context, dataId: String?) {
            val intent = Intent(mContext, HeritageSiteDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE, dataId)
            mContext.startActivity(intent)
        }

        fun getIntent(mContext: Context, dataId: String?): Intent {
            return Intent(mContext, HeritageSiteDetailActivity::class.java).apply {
                putExtra(Intent.EXTRA_TITLE, dataId)
            }
        }
    }
}