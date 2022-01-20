package com.example.haritagedemo.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.EventDetailModel
import com.example.haritagedemo.Model.HelpModel
import com.example.haritagedemo.R
import java.util.HashMap

class HelpActivity : BaseActivity() {

    var dataId: String? = null
    private lateinit var cHelpModel: HelpModel
    private var datamob:TextView = findViewById(R.id.helpData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    override fun bindViews() {
        callApiHelpLine()
    }

    fun callApiHelpLine(){
        val paramMap = HashMap<String, Any?>()
        ServiceManager(mContext).apiHelpLine(paramMap,
            object : ResponseListener<retrofit2.Response<Response<ArrayList<HelpModel?>>>>() {
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<HelpModel?>>>) {
                    super.onRequestSuccess(response)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        !responseBody.isSuccess
                        responseBody.result
                        responseBody.message
                    }
                   // setData()
                    Log.d("HelpActivity",responseBody!!.result.toString())
                }
                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }
            })

    }

//    private fun setData() {
//        datamob.text = respo
//
//    }

}