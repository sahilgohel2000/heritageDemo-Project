package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.Model.TourPackageModel
import kotlinx.android.synthetic.main.activity_tour_package.*
import kotlinx.android.synthetic.main.activity_tourism_package.*
import kotlin.math.roundToInt

class TourPackage : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    private var mpackageDetailModel: PackageDetailModel? = null
    private var mTourPackageModel: TourPackageModel? = null
    private var vCustomPagerAdapter: CustomPagerAdapter? = null
    private var vAdapter:SiteNearbyAdapter? = null
    private var vArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_package)
    }

    override fun bindViews() {

        try {
            mTourPackageModel = intent.getSerializableExtra(Intent.EXTRA_TEXT) as TourPackageModel
        }catch (e:Exception){
            e.printStackTrace()
        }

        callApi()
        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        vAdapter = SiteNearbyAdapter(
            mContext,
            vArrayList,
            this
        )
        with(walkSitesRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = vAdapter
        }
    }

    private fun callApi() {
        val serviceManager = ServiceManager(mContext)
        val hashMap = HashMap<String,Any?>()

        hashMap["package_id"]=mTourPackageModel?.id
        hashMap[Const.PARAM_LANGUAGE]="en"

        serviceManager.apiGetTourismPackageDetails(
            hashMap,
            object : ResponseListener<Response<PackageDetailModel>>(){
                override fun onRequestSuccess(response: Response<PackageDetailModel>) {
                    mpackageDetailModel = response.result!!
                    setData()
                }
            }
        )
    }

    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    private fun setData() {
        vCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mpackageDetailModel!!.fieldUploadUrl
        )
        vViewPager.adapter = vCustomPagerAdapter

        titleText.text = mpackageDetailModel!!.tourismPackageName
        walkDesc.text = stripHtml(mpackageDetailModel!!.description)

        if (mpackageDetailModel?.fieldWalkIncludedSites.isNullOrEmpty()){
            walkSitesRecycler.visibility = View.GONE
            nearbyText.visibility = View.GONE
        }else{
            vArrayList.addAll(mpackageDetailModel!!.fieldWalkIncludedSites)
            vAdapter?.notifyDataSetChanged()
            walkSitesRecycler.visibility = View.VISIBLE
            nearbyText.visibility = View.VISIBLE
        }
        startLocationText.text = mpackageDetailModel!!.fieldStartingLocationName!!.siteName
        requireTime.text = mpackageDetailModel!!.fieldTotalTimeRequired

        vratingBar.rating = mpackageDetailModel!!.ratingReview.average.roundToInt().toFloat()

        nearestBusstop.text = mpackageDetailModel!!.fieldNearestBusStationLocation
        nearestTrainStop.text = mpackageDetailModel!!.fieldNearestTrainStationLocation
        nearestAirportStop.text = mpackageDetailModel!!.fieldNearestAirportLocation

        thingstoknow.text = mpackageDetailModel!!.fieldSpecialConsideration
        timings.text = mpackageDetailModel!!.fieldTimingsForEvent
        entryfees.text = mpackageDetailModel!!.fieldEntryFeeBookingInfo
    }

    companion object {
        const val RQ_LOCATION = 1
        const val MENU_DIRECTION = 0
        const val MENU_SEARCH = 1
        const val MENU_ADD_TO_MY_ITINERARY = 2
        const val PICTURE_REQUEST = 100
        fun startActivity(
            mContext: Context,
            mData: TourPackageModel?
        ) {
            val intent = Intent(mContext, TourPackage::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, mData)
            mContext.startActivity(intent)
        }

    }

    override fun onNearBySiteClick(position: Int) {
        if (position != -1) {
            val mData = vArrayList[position]
            if (mData != null) {
                Util.openDetailsScreen(mContext, mData.type, mData.id)
            }
        }
    }
}