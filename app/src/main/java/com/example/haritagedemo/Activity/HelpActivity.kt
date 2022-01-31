package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.HelpModel
import kotlinx.android.synthetic.main.activity_help.*
import java.util.HashMap
import com.example.haritagedemo.API.BaseActivity
import com.example.haritagedemo.API.ServiceManager
import com.example.haritagedemo.Model.HeritageSiteDetailModel
import kotlinx.android.synthetic.main.items_help.*

class HelpActivity : BaseActivity(),NumberAdapter.Callback{


    private var mLayoutManager: LinearLayoutManager? = null
    private var hArrayList: ArrayList<HelpModel?> = ArrayList()
    private var hAdapter:HelpAdapter? =null

//    private lateinit var mArrayAdapter:ArrayAdapter<HelpModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    override fun bindViews() {
        Log.d("HelpActivity","bindView")
        callApiHelpLine()
        Log.d("HelpActivity","callApiHelpLine:1")

        hAdapter = HelpAdapter(mContext, hArrayList, this)
        mLayoutManager = LinearLayoutManager(mContext)
        Log.d("HelpActivity","HelpAdapter")

        with(cHelpRecylcer) {
            Log.d("HelpActivity","cHelpRecycler")

            layoutManager = mLayoutManager
            adapter = hAdapter
        }
    }

    fun callApiHelpLine() {

        val serviceManager = ServiceManager(mContext)
        val paramMap = HashMap<String, Any?>()

        serviceManager.apiHelpLine(paramMap,
            object : ResponseListener<retrofit2.Response<Response<ArrayList<HelpModel?>>>>() {
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<HelpModel?>>>) {
                    super.onRequestSuccess(response)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        !responseBody.isSuccess
                        responseBody.result
                        responseBody.message
                        hArrayList.addAll(responseBody.result!!)
                        hAdapter!!.notifyDataSetChanged()
                    }
                    Log.d("HelpActivity", responseBody!!.result.toString())
                    //responseBody!!.result?.get(0)?.let { Log.d("HelpActivity", it.helplineTitle.toString()) }


//                    helpLineTitle.text = responseBody!!.result?.get(0)?.let { it.helplineTitle.toString() }

//                    mArrayAdapter= ArrayAdapter<HelpModel>(this@HelpActivity,android.R.layout.simple_list_item_1,mArrayList)
//                    listViewFirst.adapter = mArrayAdapter

//                    for (i in responseBody.result!!){
//                        if (i != null) {
//                            nameText.text = i.helplineTitle.toString()
//                            Log.d("HelpActivity",i.helplineTitle.toString())
//                        }
//                        else{
//                            Log.d("HelpActivity",i?.helplineTitle.toString())
//                        }
//                    }

                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                    Log.d("HelpActivity","onRequestFailed")
                }
            })
    }

    override fun onHelpClick(mNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${mNumber}")
        startActivity(intent)
    }

companion object{
    fun startActivity(mContext: Context){
        val intent = Intent(mContext,HelpActivity::class.java)
        mContext.startActivity(intent)
    }
}
}


