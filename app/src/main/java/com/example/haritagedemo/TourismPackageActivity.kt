package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.Model.TourPackageResponseModel
import java.lang.Exception

class TourismPackageActivity : BaseActivity() {

    var type = Const.TOURISMPACKAGE.AMC
    private var mPackageDetailModel: PackageDetailModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourism_package)
        Log.d("TourismPackageActivity","onCreate")
    }

    override fun bindViews() {
        Log.d("TourismPackageActivity","bindview")

        callApiGetTourismPackage()
    }


    private fun callApiGetTourismPackage() {
        Log.d("TourismPackageActivity","call Api")

        val paramMap = HashMap<String,Any?>()
        paramMap[Const.PARAM_LANGUAGE] = "en"

        val serviceManager = ServiceManager(mContext)
        serviceManager.apiGetTourismPackageDetails(
            paramMap,
            object: ResponseListener<Response<PackageDetailModel>>(){
                override fun onRequestSuccess(response: Response<PackageDetailModel>) {
                    Log.d("TourismPackageActivity","OnRequestSuccess")
                    mPackageDetailModel = response.result
                    try {
                        Log.d("TourismPackageActivity",""+mPackageDetailModel!!.fieldEntryFeeBookingInfo.get(0))
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    Log.d("TourismPackageActivity","Resquest failed")
                    super.onRequestFailed(t)
                }

            }
        )
    }


}