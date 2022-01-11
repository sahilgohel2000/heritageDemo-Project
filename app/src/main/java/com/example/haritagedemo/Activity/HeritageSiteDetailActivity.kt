package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        textfirst.text = mHeritageSiteDetailModel.heritageSiteName

        mCustomPagerAdapter=CustomPagerAdapter(
            mContext,
            mHeritageSiteDetailModel.fieldUploadUrl
        )
        mViewpager.adapter = mCustomPagerAdapter
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