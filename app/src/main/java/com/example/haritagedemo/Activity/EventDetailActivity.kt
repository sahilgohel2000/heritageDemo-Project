package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.*

import com.example.haritagedemo.Activity.EventDetailActivity
import com.example.haritagedemo.AmentiesAdapter
import com.example.haritagedemo.CustomPagerAdapter
import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import com.example.haritagedemo.R
import com.example.haritagedemo.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_event_detail.mViewpager
import kotlinx.android.synthetic.main.activity_heritage_site_detail.*
import java.util.ArrayList
import java.util.HashMap

class EventDetailActivity : BaseActivity() {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null
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
        val serviceManager=ServiceManager(mContext)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

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
}