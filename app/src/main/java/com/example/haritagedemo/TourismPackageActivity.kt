package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.PackageDetailModel
import com.example.haritagedemo.Model.TourPackageModel
import com.example.haritagedemo.Model.TourPackageResponseModel
import kotlinx.android.synthetic.main.activity_tourism_package.*
import java.lang.Exception

class TourismPackageActivity : BaseActivity() {

    var type = Const.TOURISMPACKAGE.AMC
    private var mPackageDetailModel: PackageDetailModel? = null
    private var mArrayList: ArrayList<TourPackageModel?> = ArrayList()

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

                    Log.d("TourismPackageActivity","Id:"+response!!.body()!!.result!!.packages.get(0)!!.id)
                    Log.d("TourismPackageActivity","Name:"+response!!.body()!!.result!!.packages.get(0)!!.name)

                    Log.d("TourismPackageActivity","Id:"+response!!.body()!!.result!!.packages.get(1)!!.id)
                    Log.d("TourismPackageActivity","Id:"+response!!.body()!!.result!!.packages.get(1)!!.name)

//                    mArrayList.addAll(response.body()!!.result!!.packages)
  //                  Log.d("TourismPackageActivity","ArrayList:"+mArrayList.toString())

                    firstBtn.setOnClickListener(View.OnClickListener {
//                        mArrayList.add(response.body()!!.result!!.packages.get(0))
                        Toast.makeText(this@TourismPackageActivity,"you Click 1",Toast.LENGTH_SHORT).show()
                        val morningWalk:Intent = Intent(this@TourismPackageActivity,TourPackage::class.java)
                        startActivity(morningWalk)
                    })

                    secondBtn.setOnClickListener(View.OnClickListener {
                        Toast.makeText(this@TourismPackageActivity,"you Click 2",Toast.LENGTH_SHORT).show()
                        val afterWalk:Intent = Intent(this@TourismPackageActivity,TourPackage::class.java)
                        startActivity(afterWalk)
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

    companion object {
        fun startActivity(mContext: Context, type: Const.TOURISMPACKAGE) {
            val intent = Intent(mContext, TourismPackageActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, type)
            mContext.startActivity(intent)
        }
    }

}