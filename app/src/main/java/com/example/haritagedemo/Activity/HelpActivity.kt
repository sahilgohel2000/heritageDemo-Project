package com.example.haritagedemo.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.HelpAdapter
import com.example.haritagedemo.Model.HelpModel
import com.example.haritagedemo.Model.HelpNumAdapter
import com.example.haritagedemo.Model.HelpRecycleAdapter
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.item_helpline.*
import java.util.HashMap

class HelpActivity : BaseActivity() , HelpNumAdapter.Callback,HelpRecycleAdapter.RecyclerAdapterHelperCallback{

    var dataId: String? = null
    var e : String? = null
    private var mArrayList: ArrayList<HelpModel?> = ArrayList()
    private var mAdapter: HelpAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    var paginationHelper: HelpRecycleAdapter<HelpModel?>? = null
    private var hAdapter: HelpNumAdapter? = null
    private var hArrayList: ArrayList<HelpModel?>? = ArrayList()

    //private var datamob:TextView = findViewById(R.id.helpData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    override fun bindViews() {
        callApiHelpLine()
        mAdapter = HelpAdapter(mContext, mArrayList, this)
        mLayoutManager = LinearLayoutManager(mContext)
        with(cHelpRecylcer) {
            layoutManager = mLayoutManager
            addItemDecoration(
                SimpleDividerItemDecoration(
                    mContext,
                    R.drawable.item_seprator_help
                )
            )
            adapter = mAdapter
        }
//        initializationAndCallApi()
    }

//    private fun initializationAndCallApi() {
//        if (paginationHelper != null) {
//            paginationHelper!!.resetValues()
//
//            mArrayList.clear()
//            mAdapter = HelpAdapter(mContext, mArrayList, this)
//
//            mRecyclerView.adapter = mAdapter
//            callApiHelpLine()
//        }
//    }

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
                    Log.d("HelpActivity",responseBody!!.result.toString())
                }
                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }
            })

    }

//    private fun setData() {
//        //datamob.text = cHelpModel.helplineNumber.toString()
//
//    }

    override fun onHelpClick(mNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${mNumber}")
        startActivity(intent)
    }

    companion object{
        fun startActivity(mContext:Context){
            val intent = Intent(mContext,HelpActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    override fun onRetryPage() {

    }

}