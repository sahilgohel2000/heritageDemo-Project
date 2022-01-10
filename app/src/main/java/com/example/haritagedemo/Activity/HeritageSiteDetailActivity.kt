package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.haritagedemo.API.*
import com.example.haritagedemo.API.Util.openDetailsScreen
import com.example.haritagedemo.FieldNearbySitesLocation
import com.example.haritagedemo.HomeFragment
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import com.example.haritagedemo.R
import com.example.haritagedemo.heritageSiteDetailFragment
import kotlinx.android.synthetic.main.activity_heritage_site_detail.*
import kotlinx.android.synthetic.main.fragment_heritage_site_detail.*
import java.util.ArrayList
import java.util.HashMap

class HeritageSiteDetailActivity :AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heritage_site_detail)

        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.homefrag, heritageSiteDetailFragment())
        fragmentTransaction.commit()

    }


//    private fun callAPIHeritageSiteDetails() {
//        Log.e("heritagesitedetail","callApi")
//
//        val serviceManager = ServiceManager(mContext)
//        val hashMap = HashMap<String, Any?>()
//        hashMap[Const.PARAM_NID] = dataId
//        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
//        hashMap[Const.PARAM_LANGUAGE] = "en"
//
//        Log.e("heritagesitedetail","phase2")
//
//        serviceManager.apiHeritageSiteDetails(
//            hashMap,
//            object: ResponseListener<Response<HeritageSiteDetailModel>>(){
//                override fun onRequestSuccess(response: Response<HeritageSiteDetailModel>) {
//                    nHeritageSiteDetailModel=response.result!!
//                    Log.e("heritagesitedetail","phase3")
//
//                    runOnUiThread(Runnable {
//                       setupsites(
//                           nHeritageSiteDetailModel.heritageSiteName,
//                           nHeritageSiteDetailModel.description,
//                           response.result!!.nid
//                       )
//                   })
//                }
//
//
//                override fun onRequestFailed(t: Throwable) {
//                    super.onRequestFailed(t)
//                    Log.e("heritagesitedetail","onReqFailed")
//                }
//
//                override fun onRequestFailed(message: String) {
//                    super.onRequestFailed(message)
//                    Log.e("heritagesitedetail","Failed")
//                }
//            }
//        )
//    }

//    private fun setupsites(heritageSiteName: String, description: String, nid: String) {
//        Log.e("heritagesitedetail","setupsites")
//
//        txtviewhai.text=heritageSiteName
//        Log.e("heritagesitedetail","${dataId}")
//        Log.e("heritagesitedetail","${heritageSiteName}")
//    }


    companion object{
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

//    override fun onNearBySiteClick(position: Int) {
//        if (position != -1) {
//            val mData = mArrayList[position]
//            if (mData != null) {
//                openDetailsScreen(mContext, nHeritageSiteDetailModel?.type.toString(), mData.id)
//            }
//        }
//    }
}