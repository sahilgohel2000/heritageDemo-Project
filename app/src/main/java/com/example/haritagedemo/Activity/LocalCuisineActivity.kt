package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import kotlinx.android.synthetic.main.activity_local_cuisine.*
import kotlinx.android.synthetic.main.activity_local_cuisine.mViewpager
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import com.example.haritagedemo.API.Response

class LocalCuisineActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null
    private var cAdapter: SiteNearbyAdapter? = null
    private var cArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private lateinit var mLocalCuisineDetail: LocalCuisineDetail
    private var cAdapterAmenities: AmentiesAdapter? = null
    private var cArrayListAmenities: ArrayList<String?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_cuisine)
    }

    override fun bindViews() {
        dataId = intent.getStringExtra(Intent.EXTRA_TITLE)
        contentMessage =intent.getStringExtra("message")
        titleMsg = intent.getStringExtra("title")
        type = intent.getStringExtra("type")

        callAPILocalCusine()
        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        cAdapter = SiteNearbyAdapter(
            mContext,
            cArrayList,
            this
        )
        with(cuisinesitesRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal,spacingVertical))
            adapter=cAdapter
        }

        cAdapterAmenities =
            AmentiesAdapter(
                mContext,
                cArrayListAmenities
            )
        with(cuisineRecycler) {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal, spacingVertical))
            adapter = cAdapterAmenities
        }
    }

    private fun callAPILocalCusine() {
        val serviceManager=ServiceManager(mContext)
        val hashMap=HashMap<String, Any?>()

        hashMap[Const.PARAM_NID]=dataId
        hashMap[Const.PARAM_USER_ID]=mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE]="en"

        serviceManager.apiLocalCuisineDetail(
            hashMap,
            object : ResponseListener<Response<LocalCuisineDetail>>(){
                override fun onRequestSuccess(response: Response<LocalCuisineDetail>) {
                    super.onRequestSuccess(response)
                    mLocalCuisineDetail=response.result!!
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
        cuisine_title.text = mLocalCuisineDetail.heritageSiteName

        mCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mLocalCuisineDetail.fieldUploadUrl
        )
        mViewpager.adapter=mCustomPagerAdapter

        cuisine_desc.text = stripHtml(mLocalCuisineDetail.description)

        if (!mLocalCuisineDetail?.amenities!!.isNullOrEmpty()) {
            cArrayListAmenities.addAll(mLocalCuisineDetail?.amenities!!)
            cAdapterAmenities?.notifyDataSetChanged()
            cuisineRecycler.visibility = View.VISIBLE
            cuisineAmenties.visibility = View.VISIBLE
        } else {
            cuisineRecycler.visibility = View.GONE
        }

        cuisineTryItems.text = mLocalCuisineDetail.fieldMostPopularAttraction

        if (!mLocalCuisineDetail?.fieldNearbySitesLocation!!.isNullOrEmpty()){
            cArrayList.addAll(mLocalCuisineDetail?.fieldNearbySitesLocation!!)
            cAdapter?.notifyDataSetChanged()
            cuisinesitesRecycler.visibility=View.VISIBLE
            cuisinenearbySites.visibility = View.VISIBLE
        }
        else{
            cuisinesitesRecycler.visibility=View.GONE
            cuisinenearbySites.visibility = View.GONE
        }

        cuisinetxtTimeToCover.text = mLocalCuisineDetail.fieldTotalTimeRequired
        cuisinetxtTime.text = mLocalCuisineDetail.fieldTimingsForEvent

        cuisineratingBar.rating = mLocalCuisineDetail.ratingReview.average.roundToInt().toFloat()

        if (mLocalCuisineDetail.fieldRatingsAndReviewsOn == 1){
            cuisinerating.visibility = View.VISIBLE
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

    companion object{
        const val RQ_LOCATION = 1
        const val PICTURE_REQUEST = 100
        fun startActivity(mContext: Context, dataId: String?) {
            val intent = Intent(mContext, LocalCuisineActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE, dataId)
            mContext.startActivity(intent)
        }
    }

    override fun onNearBySiteClick(position: Int) {
        if (position != -1) {
            val mData = cArrayList[position]
            if (mData != null) {
                Util.openDetailsScreen(
                    mContext,
                    mLocalCuisineDetail?.type.toString(),
                    mData.id
                )
            }
        }
    }
}