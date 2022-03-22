package com.example.haritagedemo.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen
import com.example.haritagedemo.HeritageSiteDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_heritage_site_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class HeritageSiteDetailActivity :BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null
    private var latLng: LatLng? = null
    private var isPermissionFor = Const.PERMISSION.TAKE_PHOTO

    private var mAdapter: SiteNearbyAdapter? = null
    private var mArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private var mAdapterAmenities: AmentiesAdapter? = null
    private var mArrayListAmenities: ArrayList<String?> = ArrayList()
    var isPlay:Boolean = false
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

//        getThereBtn.setOnClickListener {
//            Toast.makeText(this,"Get There",Toast.LENGTH_SHORT).show()
//
//        }

        mAdapter = SiteNearbyAdapter(
            mContext,
            mArrayList,
            this
        )
        with(sitesRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical, spacingHorizontal))
            adapter = mAdapter
        }

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
        hashMap[Const.PARAM_LANGUAGE] = mPreferanceManager.getLanguage()?.code

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

        //heritage site Titile
        heritage_title.text = mHeritageSiteDetailModel.heritageSiteName

        //heritage site Images
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

        txtTimeToCover.text = mHeritageSiteDetailModel.fieldTotalTimeRequired
        txtTime.text = mHeritageSiteDetailModel.fieldTimingsForSite
        txtFees.text = mHeritageSiteDetailModel.fieldEntryFeeBookingInfo.toString()

        if (mHeritageSiteDetailModel.fieldSpecialConsideration.isNullOrEmpty()){
            textThings.visibility = View.GONE
            textttk.visibility = View.GONE
        }
        else{
            textThings.text=mHeritageSiteDetailModel.fieldSpecialConsideration
            textThings.visibility = View.VISIBLE
            textttk.visibility = View.VISIBLE
        }

        if (mHeritageSiteDetailModel.fieldMostPopularAttraction.isNullOrEmpty())
        {
            textPopularAttraction.visibility = View.GONE
            textPopular.visibility = View.GONE
        }
        else{
            textPopularAttraction.text = mHeritageSiteDetailModel.fieldMostPopularAttraction
            textPopularAttraction.visibility = View.VISIBLE
            textPopular.visibility = View.VISIBLE
        }

        if (mHeritageSiteDetailModel.type.equals("local_cuisine",true)){
            textPopular.text = "Must try Item"
        }
        else{
            textPopular.text = "Most Popular Attraction"
        }

        ratingBar.rating = mHeritageSiteDetailModel.ratingReview.average.roundToInt().toFloat()

        if (mHeritageSiteDetailModel.fieldRatingsAndReviewsOn == 1){
            rating.visibility = View.VISIBLE
        }

        Log.d("HeritageSiteDetail",mHeritageSiteDetailModel.fieldNearbySitesLocation.toString())
        if (mHeritageSiteDetailModel?.fieldNearbySitesLocation!!.isNullOrEmpty()){
            sitesRecycler.visibility = View.GONE
            nearbySites.visibility = View.GONE
        }else{
            mArrayList.addAll(mHeritageSiteDetailModel?.fieldNearbySitesLocation!!)
            mAdapter?.notifyDataSetChanged()
            sitesRecycler.visibility = View.VISIBLE
            nearbySites.visibility = View.VISIBLE
        }
        //nearbySites.text = mHeritageSiteDetailModel.fieldNearbySitesLocation.toString()

        nearestBus.text = mHeritageSiteDetailModel.fieldNearestBusStationLocation.toString()
        nearestTrain.text = mHeritageSiteDetailModel.fieldNearestTrainStationLocation.toString()
        nearestAirport.text = mHeritageSiteDetailModel.fieldNearestAirportLocation.toString()

//        floatingBtn.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this,"Button is Working",Toast.LENGTH_LONG).show()
////            floatingBtn.visibility = View.GONE
//            floatingBtn.setImageResource(
//                if (isPlay)
//                    R.drawable.cross_button
//                else
//                    R.drawable.plus_sign
//            )
//
//            isPlay = !isPlay
//
//        })

        getThereBtn.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mHeritageSiteDetailModel.latitude, mHeritageSiteDetailModel.longitude)

            Util.showGetThereDialog(
                this,
                mHeritageSiteDetailModel.translateName,
                mHeritageSiteDetailModel.latitude,
                mHeritageSiteDetailModel.longitude,
                latLng
            )
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

    override fun onNearBySiteClick(position: Int) {
        if (position != -1) {
            val mData = mArrayList[position]
            if (mData != null) {
                openDetailsScreen(mContext, mHeritageSiteDetailModel?.type.toString(), mData.id)
            }
        }
    }
}


