package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.*
import com.example.haritagedemo.CustomPagerAdapter
import com.example.haritagedemo.Model.AboutAhmedabadModel
import com.example.haritagedemo.Model.WebsiteLinkModel
import com.example.haritagedemo.R
import com.example.haritagedemo.RelatedSiteAdapter
import com.example.haritagedemo.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_about_ahmedabad.*

class AboutAhmedabadActivity : BaseActivity(),RelatedSiteAdapter.Callback {

    private var aCustomPagerAdapter: CustomPagerAdapter? = null
    private var aArrayList: ArrayList<WebsiteLinkModel?> = ArrayList()
    private var aAdapter: RelatedSiteAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_ahmedabad)
    }

    override fun bindViews() {
        showProgressView()

        callApiAboutAhmedabad()

        val spacingVertical = resources.getDimensionPixelSize(R.dimen._zero_dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        aAdapter = RelatedSiteAdapter(
            mContext, aArrayList, this
        )
        with(siteRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical,spacingHorizontal))
            adapter = aAdapter
        }

    }

    private fun callApiAboutAhmedabad() {
        showProgressView()
        val serviceManager = ServiceManager(mContext)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_LANGUAGE] = mPreferanceManager.getLanguage()?.code

        serviceManager.apiGetAboutAhmedabad(
            hashMap,
            object : ResponseListener<Response<AboutAhmedabadModel>>(){
                override fun onRequestSuccess(response: Response<AboutAhmedabadModel>) {
                    showProgressView()

                    if (response.result != null)
                        setData(response.result!!)
                }

                override fun onRequestFailed(t: Throwable) {
                    showProgressView()

                    super.onRequestFailed(t)
                }

                override fun onRequestFailed(message: String) {
                    showProgressView()

                    super.onRequestFailed(message)
                }
            }
        )
    }

    private fun setData(mData: AboutAhmedabadModel) {
        showProgressView()

        aCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mData.fieldImageUrl
        )
        aViewPager.adapter = aCustomPagerAdapter

        headText.text = mData.title
        aboutDesc.text = stripHtml(mData.description)

        visitTime.text = mData.fieldBestTimeToVisits

        BusStop.text = mData.fieldNearestBusStation
        TrainStop.text = mData.fieldNearestTrainLocation
        Airport.text = mData.fieldNearestAirportLocation

        aArrayList.addAll(mData.fieldRelatedWebsiteLink)
        aAdapter?.notifyDataSetChanged()
        siteRecycler.visibility = View.VISIBLE

    }
    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    companion object {
        var MENU_SEARCH = 0
        fun startActivity(mContext: Context) {
            val intent = Intent(mContext, AboutAhmedabadActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    override fun onItemClick(mData: WebsiteLinkModel) {
        val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mData.link))
        startActivity(mIntent)
    }
}