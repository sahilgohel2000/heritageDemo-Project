package com.example.haritagedemo.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.*
import com.example.haritagedemo.Model.RelatedLinkModel
import com.example.haritagedemo.Model.WebsiteLinkModel
import com.example.haritagedemo.R
import com.example.haritagedemo.RelatedLinkAdapter
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import kotlinx.android.synthetic.main.activity_related_link.*

class relatedLinkActivity : BaseActivity(),RelatedLinkAdapter.CallBack {

    private var mAdapterRelated:RelatedLinkAdapter? = null
    private val vArrayList:ArrayList<RelatedLinkModel?> = ArrayList()
    private var mLayoutManager:LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_related_link)
        Log.d("relatedLinkActivity","onCreate()")
    }

    override fun bindViews() {
        Log.d("relatedLinkActivity","bindViews()")
        initializeCallApi()
        mAdapterRelated = RelatedLinkAdapter(mContext,vArrayList,this)
        mLayoutManager = LinearLayoutManager(mContext)
        with(linkTitles){
            layoutManager = mLayoutManager
            adapter = mAdapterRelated
        }
    }

    private fun initializeCallApi() {
        Log.d("relatedLinkActivity","CallApi")
        val paramMap=HashMap<String,Any?>()
        ServiceManager(mContext).apiGetRelatedLink(
            paramMap,
            object : ResponseListener<retrofit2.Response<Response<ArrayList<RelatedLinkModel?>>>>(

            ){
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<RelatedLinkModel?>>>) {
                    val responseBody= response.body()
                    Log.d("relatedLinkActivity","Response"+responseBody.toString())
                    Log.d("relatedLinkActivity","Response"+response.body()!!.result.toString())

                    if (responseBody != null && responseBody.code ==Const.SUCCESS){
                        setRelatedLink(responseBody)
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                }

                override fun onRequestFailed(message: String) {
                    super.onRequestFailed(message)
                }

            }
        )
    }

    private fun setRelatedLink(responseBody: Response<java.util.ArrayList<RelatedLinkModel?>>) {
        Log.d("relatedLinkActivity","setRelated Link 1:"+responseBody.result.toString())
        Log.d("relatedLinkActivity","setRelated Link 2:"+responseBody.result)
        vArrayList.addAll(responseBody.result!!)
        mAdapterRelated!!.notifyDataSetChanged()
    }

    override fun OnRelatedLink(pos: RelatedLinkModel) {
        val mIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pos.link))
        startActivity(mIntent)
    }


}