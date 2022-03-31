package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen

import com.example.haritagedemo.Model.EventDetailModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_event_detail.mViewpager
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class EventDetailActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null

    private var latLng: LatLng? = null
    private var isPermissionFor = Const.PERMISSION.TAKE_PHOTO

    private var eAdapter: SiteNearbyAdapter? = null
    private var eArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private lateinit var mEventDetailModel: EventDetailModel
    private var eAdapterAmenities: AmentiesAdapter? = null
    private var eArrayListAmenities: ArrayList<String?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
    }

    override fun bindViews() {
        dataId = intent.getStringExtra(Intent.EXTRA_TITLE)
        contentMessage =intent.getStringExtra("message")
        titleMsg = intent.getStringExtra("title")
        type = intent.getStringExtra("type")

        callAPIEventDetail()
        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        eAdapter = SiteNearbyAdapter(
            mContext,
            eArrayList,
            this
        )
        with(eventsitesRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = eAdapter
        }

        eAdapterAmenities =
            AmentiesAdapter(
                mContext,
                eArrayListAmenities
            )
        with(eventRecycler) {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpacesItemDecoration(spacingHorizontal, spacingVertical))
            adapter = eAdapterAmenities
        }
    }

    private fun callAPIEventDetail() {

        if (!Util.isConnectedtoInternet(mContext)){
            Util.showMessage(mContext,getString(R.string.error_no_internet))
            return
        }

        val serviceManager=ServiceManager(mContext)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = mPreferanceManager.getLanguage()?.code

        serviceManager.apiEventDetail(
            hashMap,
            object : ResponseListener<Response<EventDetailModel>>(){
                override fun onRequestSuccess(response: Response<EventDetailModel>) {
                    super.onRequestSuccess(response)
                    mEventDetailModel = response.result!!
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
        event_title.text = mEventDetailModel.eventName
        mCustomPagerAdapter=CustomPagerAdapter(
            mContext,
            mEventDetailModel.fieldUploadUrl
        )
        mViewpager.adapter=mCustomPagerAdapter
        event_desc.text = stripHtml(mEventDetailModel.description)

        if (!mEventDetailModel?.amenities!!.isNullOrEmpty()) {
            eArrayListAmenities.addAll(mEventDetailModel?.amenities!!)
            eAdapterAmenities?.notifyDataSetChanged()
            eventRecycler.visibility = View.VISIBLE
            eventAmenties.visibility = View.VISIBLE
        } else {
            eventRecycler.visibility = View.GONE
        }

        eventTimeToCover.text = mEventDetailModel.fieldTotalTimeRequired

        if (mEventDetailModel.fieldSpecialConsideration.isNullOrEmpty()){
            eventThings.visibility = View.GONE
            eventttk.visibility = View.GONE
        }
        else{
            eventThings.text=mEventDetailModel.fieldSpecialConsideration
            eventThings.visibility = View.VISIBLE
            eventttk.visibility = View.VISIBLE
        }

        eventTime.text = mEventDetailModel.fieldTimingsForEvent
        eventFees.text = mEventDetailModel.fieldEntryFeeBookingInfo.toString()

        eventratingBar.rating = mEventDetailModel.ratingReview.average.roundToInt().toFloat()

        if (mEventDetailModel.fieldRatingsAndReviewsOn == 1){
            eventrating.visibility = View.VISIBLE
        }

        if (mEventDetailModel?.fieldNearbySitesLocation!!.isNullOrEmpty()){
            Toast.makeText(this,"invalid data",Toast.LENGTH_LONG).show()
        }
        else{
            eArrayList.addAll(mEventDetailModel?.fieldNearbySitesLocation!!)
            eAdapter?.notifyDataSetChanged()
            eventsitesRecycler.visibility = View.VISIBLE
            eventnearbySites.visibility = View.VISIBLE
        }

        txtLocationOfEvent.text = mEventDetailModel.fieldLocationOfEvent

        eventnearestBus.text = mEventDetailModel.fieldNearestBusStationLocation.toString()
        eventnearestTrain.text = mEventDetailModel.fieldNearestTrainStationLocation.toString()
        eventnearestAirport.text = mEventDetailModel.fieldNearestAirportLocation.toString()

        getThereBtn.setOnClickListener {
            isPermissionFor = Const.PERMISSION.BOOK_CAB
            latLng = LatLng(mEventDetailModel.latitude, mEventDetailModel.longitude)

            Util.showGetThereDialog(
                this,
                mEventDetailModel.transLateName,
                mEventDetailModel.latitude,
                mEventDetailModel.longitude,
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
        const val RQ_LOCATION = 1
        const val PICTURE_REQUEST = 100

        fun startActivity(mContext: Context, dataId: String?) {
            val intent = Intent(mContext, EventDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_TITLE, dataId)
            mContext.startActivity(intent)
        }
    }

    override fun onNearBySiteClick(position: Int) {
        if (position != -1){
            val mData = eArrayList[position]
            if (mData != null){
                openDetailsScreen(mContext, mEventDetailModel?.type.toString(), mData.id )
            }
        }
    }
}