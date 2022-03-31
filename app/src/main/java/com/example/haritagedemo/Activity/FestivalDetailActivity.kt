package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen
import com.example.haritagedemo.Model.FestivalDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_festival_detail.*
import kotlinx.android.synthetic.main.activity_festival_detail.mViewpager
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class FestivalDetailActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null

    private var latLng: LatLng? = null
    private var isPermissionFor = Const.PERMISSION.TAKE_PHOTO

    private var fAdapter:SiteNearbyAdapter? = null
    private var fArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private lateinit var mFestivalDetailModel: FestivalDetailModel
    private var fAdapterAmenities: AmentiesAdapter? = null
    private var fArrayListAmenities: ArrayList<String?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_festival_detail)
    }

    override fun bindViews() {
        dataId = intent.getStringExtra(Intent.EXTRA_TITLE)
        contentMessage =intent.getStringExtra("message")
        titleMsg = intent.getStringExtra("title")
        type = intent.getStringExtra("type")

        callAPIGetFestivalDetails()

        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        fAdapter = SiteNearbyAdapter(
            mContext,
            fArrayList,
            this
        )
        with(festivalsitesRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = fAdapter
        }

        fAdapterAmenities =
            AmentiesAdapter(
                mContext,
                fArrayListAmenities
            )
        with(festivalRecycler) {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal, spacingVertical))
            adapter = fAdapterAmenities
        }

    }

    private fun callAPIGetFestivalDetails() {

        if (!Util.isConnectedtoInternet(mContext)){
            Util.showMessage(mContext,getString(R.string.error_no_internet))
            return
        }

        val serviceManager=ServiceManager(mContext)
        val hashMap=HashMap<String, Any?>()

        hashMap[Const.PARAM_NID]=dataId
        hashMap[Const.PARAM_USER_ID]=mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferanceManager.getLanguage()?.code

        serviceManager.apiGetFestivalDetails(
            hashMap,
            object : ResponseListener<Response<FestivalDetailModel>>(){
                override fun onRequestSuccess(response: Response<FestivalDetailModel>) {
                    super.onRequestSuccess(response)
                    mFestivalDetailModel = response!!.result!!
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
        festival_title.text = mFestivalDetailModel.heritageSiteName

        mCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mFestivalDetailModel.fieldUploadUrl
        )
        mViewpager.adapter = mCustomPagerAdapter
        festival_desc.text = stripHtml(mFestivalDetailModel.description)

        if (!mFestivalDetailModel?.amenities!!.isNullOrEmpty()) {
            fArrayListAmenities.addAll(mFestivalDetailModel?.amenities!!)
            fAdapterAmenities?.notifyDataSetChanged()
            festivalRecycler.visibility = View.VISIBLE
            festivalAmenties.visibility = View.VISIBLE
        } else {
            festivalRecycler.visibility = View.GONE
        }

        if (mFestivalDetailModel.fieldSpecialConsideration.isNullOrEmpty()){
            festivaltextThings.visibility = View.GONE
            festivaltextttk.visibility = View.GONE
        }
        else{
            festivaltextThings.text=mFestivalDetailModel.fieldSpecialConsideration
            festivaltextThings.visibility = View.VISIBLE
            festivaltextttk.visibility = View.VISIBLE
        }

        festivaltxtTime.text = mFestivalDetailModel.fieldTimingsOfFestival
        festivaltxtFees.text = mFestivalDetailModel.fieldEntryFeeBookingInfo.toString()

        festivalratingBar.rating = mFestivalDetailModel.ratingReview.average.roundToInt().toFloat()

        if (mFestivalDetailModel.fieldRatingsAndReviewsOn == 1){
            festivalrating.visibility = View.VISIBLE
        }

        if (!mFestivalDetailModel?.fieldSelectSpecialCelebrationLocations!!.isNullOrEmpty())
        {
            fArrayList.addAll(mFestivalDetailModel?.fieldSelectSpecialCelebrationLocations!!)
            fAdapter?.notifyDataSetChanged()
            festivalRecycler.visibility = View.VISIBLE
            festivalVisit.visibility = View.VISIBLE
        }else{
            festivalRecycler.visibility = View.GONE
            festivalVisit.visibility = View.GONE
        }
        btnGetThere.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mFestivalDetailModel.latitude, mFestivalDetailModel.longitude)

            Util.showGetThereDialog(
                this,
                mFestivalDetailModel.heritageSiteName,
                mFestivalDetailModel.latitude,
                mFestivalDetailModel.longitude,
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

    companion object{
    const val PICTURE_REQUEST = 100
    fun startActivity(mContext: Context, dataId: String?) {
        val intent = Intent(mContext, FestivalDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TITLE, dataId)
        mContext.startActivity(intent)
    }
}

    override fun onNearBySiteClick(position: Int) {
        if (position != -1){
            val mData = fArrayList[position]
            if (mData != null){
                openDetailsScreen(mContext, mData?.type.toString(), mData.id)
            }
        }
    }
}

private fun <E> ArrayList<E>.addAll(elements: ArrayList<Any>) {
    TODO("Not yet implemented")
}
