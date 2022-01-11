package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haritagedemo.API.*
import com.example.haritagedemo.CustomPagerAdapter
import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.FestivalDetailModel
import com.example.haritagedemo.R
import kotlinx.android.synthetic.main.activity_festival_detail.*

class FestivalDetailActivity : BaseActivity() {

    var dataId: String? = null
    var contentMessage: String? = null
    var titleMsg: String? = null
    var type: String? = null
    private var mCustomPagerAdapter: CustomPagerAdapter? = null
    private lateinit var mFestivalDetailModel: FestivalDetailModel

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
    }

    private fun callAPIGetFestivalDetails() {
        val serviceManager=ServiceManager(mContext)
        val hashMap=HashMap<String, Any?>()

        hashMap[Const.PARAM_NID]=dataId
        hashMap[Const.PARAM_USER_ID]=mPreferanceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE]="en"

        serviceManager.apiGetFestivalDetails(
            hashMap,
            object : ResponseListener<Response<FestivalDetailModel>>(){
                override fun onRequestSuccess(response: Response<FestivalDetailModel>) {
                    super.onRequestSuccess(response)
                    mFestivalDetailModel = response.result!!
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
        festival_name.text = mFestivalDetailModel.heritageSiteName

        mCustomPagerAdapter = CustomPagerAdapter(
            mContext,
            mFestivalDetailModel.fieldUploadUrl
        )
        mViewpager.adapter = mCustomPagerAdapter
    }

    companion object{
    const val PICTURE_REQUEST = 100
    fun startActivity(mContext: Context, dataId: String?) {
        val intent = Intent(mContext, FestivalDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_TITLE, dataId)
        mContext.startActivity(intent)
    }
}
}