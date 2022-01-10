package com.example.haritagedemo

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import kotlinx.android.synthetic.main.fragment_heritage_site_detail.*
import java.util.ArrayList


class heritageSiteDetailFragment : BaseFragment() {

    private var mHeritageSiteDetailModel: HeritageSiteDetailModel?=null
    var dataId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("phases","phase1")
    }

    override fun bindViews(view: View) {
        Log.d("phases","phase2")

        callAPIHeritageSiteDetails()
    }

    fun callAPIHeritageSiteDetails(){
        Log.d("phases","phase3")

        val serviceManager = ServiceManager(mContext!!)
        val hashMap = HashMap<String, Any?>()

        hashMap[Const.PARAM_NID] = dataId
        hashMap[Const.PARAM_USER_ID] = mPreferenceManager.getUserId()
        hashMap[Const.PARAM_LANGUAGE] = "en"

        serviceManager.apiHeritageSiteDetails(
            hashMap,
            object : ResponseListener<Response<HeritageSiteDetailModel>>() {
                override fun onRequestSuccess(response: Response<HeritageSiteDetailModel>) {
//                    super.onRequestSuccess(response)
                    mHeritageSiteDetailModel = response.result
                    Log.d("phases","phase4")
                    Log.d("phases",response.result.toString())

                    activity!!.runOnUiThread(Runnable {
                        Log.d("heritagesitedetail", "HeritageName:"+mHeritageSiteDetailModel!!.heritageSiteName)
                        setupBottomSite(
                            dataId,
                            mHeritageSiteDetailModel!!.heritageSiteName,
                            mHeritageSiteDetailModel!!.description,
                            mHeritageSiteDetailModel!!.fieldHeritageSitesCategory.joinToString(
                                separator = ""
                            ),
                            mHeritageSiteDetailModel!!.type,
                            response.result!!.nid,
                            mHeritageSiteDetailModel!!.fieldUploadUrl.get(0),
                            mHeritageSiteDetailModel!!.amenities,
                            Util.getLocation(
                                mHeritageSiteDetailModel!!.latitude,
                                mHeritageSiteDetailModel!!.longitude
                            )
                        )
                    })
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                    Log.d("phases","phase5")
                }

                override fun onRequestFailed(message: String) {
                    super.onRequestFailed(message)
                    Log.d("phases","phase6")
                }
            }
        )
    }

    private fun setupBottomSite(
        dataId: String?,
        heritageSiteName: String,
        description: String,
        joinToString: String,
        type: String,
        nid: String,
        get: String,
        amenities: ArrayList<String>,
        location: Location
    ) {
        Log.d("phases","phase7")
        finalytext.text = heritageSiteName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heritage_site_detail, container, false)
    }


}