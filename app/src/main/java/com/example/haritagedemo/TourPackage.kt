package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import kotlinx.android.synthetic.main.activity_tour_package.*
import kotlinx.android.synthetic.main.activity_tourism_package.*

class TourPackage : AppCompatActivity() {

    private var mpackageDetailModel: PackageDetailModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_package)
        Log.d("TourPackage","Oncreate")
        Log.d("TourPackage","api function")
        callApi()
    }

    private fun callApi() {
        Log.d("TourPackage","Call Api")

        val serviceManager = ServiceManager(this)
        val hashMap = HashMap<String,Any?>()

        hashMap["package_id"]=640

        hashMap[Const.PARAM_LANGUAGE]="en"
        Log.d("TourPackage","Language Set")
        serviceManager.apiGetTourismPackageDetails(
            hashMap,
            object : ResponseListener<Response<PackageDetailModel>>(){
                override fun onRequestSuccess(response: Response<PackageDetailModel>) {
                    Log.d("TourPackage","Response Aaya")

                    mpackageDetailModel = response.result!!
                    Log.d("TourPackage","setData Call"+response.result)
                    setData()
                }
            }
        )
    }

    private fun stripHtml(description: String): CharSequence? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        }else{
            return Html.fromHtml(description).toString()
        }
    }

    private fun setData() {
        Log.d("TourPackage","SetData")
        Log.d("TourPackage","result1"+mpackageDetailModel!!.tourismPackageName.toString())
        titleText.text = mpackageDetailModel!!.tourismPackageName
        titleTextView.text = stripHtml(mpackageDetailModel!!.description)
    }

}