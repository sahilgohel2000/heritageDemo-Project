package com.example.haritagedemo.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.HeritageWalkModel
import kotlinx.android.synthetic.main.activity_heritage_walk.*
import kotlin.math.roundToInt

class HeritageWalkActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    private var mType: Const.HERITAGEWALK = Const.HERITAGEWALK.MORNING
    private var mHeritageWalkModel : HeritageWalkModel? = null
    private var mArrayList:ArrayList<HeritageWalkModel?> = ArrayList()
    private var mCustomPagerAdapter:CustomPagerAdapter? = null
    private var mAmentiesAdapter:AmentiesAdapter? = null
    private var mArrayListAmenities: ArrayList<String?> = ArrayList()
    private var mAdapter: SiteNearbyAdapter? = null
    private var nArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heritage_walk)
    }

    override fun bindViews() {

        mType = intent.getSerializableExtra(Intent.EXTRA_TITLE) as Const.HERITAGEWALK

        callHeritageWalkApi()

        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        mAdapter = SiteNearbyAdapter(
            mContext,
            nArrayList,
            this
        )
        with(nearbySitesRecycler){
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = mAdapter
        }

        mAmentiesAdapter = AmentiesAdapter(
            mContext,
            mArrayListAmenities
        )
        with(amentiesRec){
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal, spacingVertical))
            adapter = mAmentiesAdapter
        }
    }

    private fun callHeritageWalkApi() {

        val hashMap = HashMap<String,Any?>()
//        hashMap[Const.PARAM_LANGUAGE] = "gu"
        hashMap[Const.PARAM_LANGUAGE]=mPreferanceManager.getLanguage()!!.code
        hashMap["category"] = mType.toString().toLowerCase()

        val serviceManager = ServiceManager(mContext)
        serviceManager.apiHeritageWalkDetail(
            hashMap,
            object : ResponseListener<Response<HeritageWalkModel>>(){
                override fun onRequestSuccess(response: Response<HeritageWalkModel>) {
                    mHeritageWalkModel = response.result
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setData() {
        walkTitle.text = mHeritageWalkModel!!.walkCategoryName

        mCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mHeritageWalkModel!!.fieldImageUrl
        )
        wViewpager.adapter = mCustomPagerAdapter

        Desc.text = stripHtml(mHeritageWalkModel!!.description)

            mArrayListAmenities.addAll(mHeritageWalkModel!!.amenities)
            mAmentiesAdapter!!.notifyDataSetChanged()
            amentiesRec.visibility = View.VISIBLE
            heritageamenties.visibility = View.VISIBLE

        if (mHeritageWalkModel!!.fieldWalkIncludedSites.isNullOrEmpty()){
            nearbySitesRecycler.visibility = View.GONE
            nearSites.visibility = View.GONE
        }else{
            nArrayList.addAll(mHeritageWalkModel!!.fieldWalkIncludedSites)
            mAdapter!!.notifyDataSetChanged()
            nearbySitesRecycler.visibility = View.VISIBLE
            nearSites.visibility = View.VISIBLE
        }

        walkingTime.text = mHeritageWalkModel!!.fieldTotalTimeRequired
        walkratingBar.rating = mHeritageWalkModel!!.ratingReview.average.roundToInt().toFloat()

        busStation.text = mHeritageWalkModel!!.fieldNearestBusStationLocation
        trainStation.text = mHeritageWalkModel!!.fieldNearestTrainStationLocation
        airportStation.text = mHeritageWalkModel!!.fieldNearestAirportLocation

        thingstoknowText.text = stripHtml(mHeritageWalkModel!!.fieldSpecialConsideration)
        timing.text = mHeritageWalkModel!!.fieldTimingsForWalk

        fees.text = mHeritageWalkModel!!.fieldEntryFeeBookingInfo
    }

    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    companion object {
        const val PICTURE_REQUEST = 100
        const val RQ_LOCATION = 1
        const val MENU_DIRECTION = 0
        const val MENU_SEARCH = 1
        const val MENU_MAP = 2
        fun startActivity(mContext: Context, type: Const.HERITAGEWALK) {
            val intent = Intent(mContext, HeritageWalkActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE, type)
            mContext.startActivity(intent)
        }
    }

    override fun onNearBySiteClick(position: Int) {
        if (position != -1) {
            val mData = nArrayList[position]
            if (mData != null) {
                Util.openDetailsScreen(mContext,mData.type,mData.id)
            }
        }
    }
}