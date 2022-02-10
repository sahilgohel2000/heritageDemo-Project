package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.Model.TourPackageModel
import kotlinx.android.synthetic.main.activity_tour_package.*
import kotlinx.android.synthetic.main.activity_tourism_package.*

class TourPackage : BaseActivity() {

    private var mpackageDetailModel: PackageDetailModel? = null
    private var mTourPackageModel: TourPackageModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_package)
        Log.d("TourPackage","Oncreate")
        Log.d("TourPackage","api function")
    }

    override fun bindViews() {
        try {
            mTourPackageModel = intent.getSerializableExtra(Intent.EXTRA_TEXT) as TourPackageModel
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("TourPackage","Exception"+e.toString())
        }
        callApi()
    }

    private fun callApi() {
        Log.d("TourPackage","Call Api")

        val serviceManager = ServiceManager(mContext)
        val hashMap = HashMap<String,Any?>()

//        val tourismPackageActivity = TourismPackageActivity()
//        hashMap["package_id"]=tourismPackageActivity.tArrayList?.get(tourismPackageActivity.tArrayList.size)?.id
        hashMap["package_id"]=mTourPackageModel?.id

        Log.d("TourPackage","Its okay"+mTourPackageModel?.id)

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

    companion object {
        const val RQ_LOCATION = 1
        const val MENU_DIRECTION = 0
        const val MENU_SEARCH = 1
        const val MENU_ADD_TO_MY_ITINERARY = 2
        const val PICTURE_REQUEST = 100
        fun startActivity(
            mContext: Context,
            mData: TourPackageModel?
        ) {
            Log.d("TourPackage","MData:"+mData)
            val intent = Intent(mContext, TourPackage::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, mData)
            mContext.startActivity(intent)
        }

    }

}