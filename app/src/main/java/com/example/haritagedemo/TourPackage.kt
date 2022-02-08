package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import kotlinx.android.synthetic.main.activity_tour_package.*
import kotlinx.android.synthetic.main.activity_tourism_package.*

class TourPackage : BaseActivity() {

    lateinit var packageDetailModel: PackageDetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_package)
        Log.d("TourPackage","Oncreate")
    }

    override fun bindViews() {
        Log.d("TourPackage","bind view")

        try {
            Log.d("TourPackage","Try")
            callApi()
        }catch (e:Exception){
            Log.d("TourPackage","catch")
            e.printStackTrace()
        }
    }

    private fun callApi() {
        Log.d("TourPackage","Call Api")

        val hashMap = HashMap<String,Any?>()
        hashMap["package_id"]=packageDetailModel.nid[640]
        hashMap[Const.PARAM_LANGUAGE]="en"
        ServiceManager(mContext).apiGetTourismPackageDetails(
            hashMap,
            object : ResponseListener<Response<PackageDetailModel>>(){
                override fun onRequestSuccess(response: Response<PackageDetailModel>) {
                    val responsebody = response
                    Log.d("TourPackage","Res1:"+response.result!!.tourismPackageName)
                    Log.d("TourPackage","Res2:"+responsebody.result!!.tourismPackageName)
                    Log.d("TourPackage","Res3"+response.result.toString())
                    Log.d("TourPackage","Res4"+response)
                    titleText.text = response.result!!.tourismPackageName.toString()
                }
            }
        )
    }

}