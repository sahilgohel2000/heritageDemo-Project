package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.Model.TourPackageModel
import com.example.haritagedemo.Model.TourPackageResponseModel
import kotlinx.android.synthetic.main.activity_tourism_package.*
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

        callApiGetTourismPackages()
    }

    private fun callApiGetTourismPackages() {
        val paramMap = HashMap<String, Any?>()
        paramMap["package_name"] = "amc".takeIf { type == Const.TOURISMPACKAGE.AMC }?:"nomac"
        paramMap[Const.PARAM_LANGUAGE] = "en"

        val serviceManager = ServiceManager(mContext)
        serviceManager.getTourismPackages(
            paramMap,
            object : ResponseListener<retrofit2.Response<Response<TourPackageResponseModel>>>(){
                override fun onRequestSuccess(response: retrofit2.Response<Response<TourPackageResponseModel>>) {
                    val responsebody = response.body()
                    Log.d("TourismPackageActivity","Result1"+responsebody)
                    Log.d("TourismPackageActivity","Result2"+responsebody!!.result)

                    resultText.text = responsebody.result!!.description
                    firstBtn.text = responsebody.result!!.packages.get(0)!!.name
                    secondBtn.text = responsebody.result!!.packages.get(1)!!.name

                    firstBtn.setOnClickListener(View.OnClickListener {
                        Toast.makeText(this@TourismPackageActivity,"you Click 1",Toast.LENGTH_SHORT).show()
                    })

                    secondBtn.setOnClickListener(View.OnClickListener {
                        Toast.makeText(this@TourismPackageActivity,"you Click 2",Toast.LENGTH_SHORT).show()
                    })

                    if (responsebody != null && response.code() == Const.SUCCESS){
                        if (responsebody.result?.packages.isNullOrEmpty()){
                            Log.d("TourismPackageActivity","Result3"+responsebody.result!!.description)
                        }
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }

            }
        )
    }



}