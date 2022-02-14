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
        Log.d("TourPackage","Oncreate")
        Log.d("TourPackage","api function")
    }

    override fun bindViews() {
        try {
            mTourPackageModel = intent.getSerializableExtra(Intent.EXTRA_TEXT) as TourPackageModel
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("TourPackage","Exception"+e.toString())
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
            Log.d("TourPackage","Adapter ser=t for Location")
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = vAdapter
        }
    }

    private fun callApi() {
        Log.d("TourPackage","Call Api")

        val serviceManager = ServiceManager(mContext)
        val hashMap = HashMap<String,Any?>()

//        val tourismPackageActivity = TourismPackageActivity()
//        hashMap["package_id"]=tourismPackageActivity.tArrayList?.get(tourismPackageActivity.tArrayList.size)?.id
        hashMap["package_id"]=mTourPackageModel?.id

        Log.d("TourPackage","Its okay"+mTourPackageModel?.id)

        hashMap[Const.PARAM_LANGUAGE]="en"
        Log.d("TourPackage","Language Set")
        serviceManager.apiGetTourismPackageDetails(
            hashMap,
            object : ResponseListener<Response<PackageDetailModel>>(){
                override fun onRequestSuccess(response: Response<PackageDetailModel>) {
                    Log.d("TourPackage","Response Aaya")

                    mpackageDetailModel = response.result!!
                    Log.d("TourPackage","setData Call"+response.result)
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
        Log.d("TourPackage","SetData")

        vCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mpackageDetailModel!!.fieldUploadUrl
        )

        vViewPager.adapter = vCustomPagerAdapter

        Log.d("TourPackage","result1"+mpackageDetailModel!!.tourismPackageName.toString())
        titleText.text = mpackageDetailModel!!.tourismPackageName
        walkDesc.text = stripHtml(mpackageDetailModel!!.description)

        if (mpackageDetailModel?.fieldWalkIncludedSites.isNullOrEmpty()){
            Log.d("TourPackage","If Condtion+Location")
            walkSitesRecycler.visibility = View.GONE
            nearbyText.visibility = View.GONE
        }else{
            Log.d("TourPackage","else Condtion+Location")
            vArrayList.addAll(mpackageDetailModel!!.fieldWalkIncludedSites)
            vAdapter?.notifyDataSetChanged()
            walkSitesRecycler.visibility = View.VISIBLE
            nearbyText.visibility = View.VISIBLE
        }
Log.d("TourPackage","Starting Location"+mpackageDetailModel!!.fieldStartingLocationName!!.siteName)

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
            Log.d("TourPackage","MData:"+mData)
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