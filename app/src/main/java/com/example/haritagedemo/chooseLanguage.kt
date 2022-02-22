package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.*
import kotlinx.android.synthetic.main.activity_choose_language.*
import java.lang.Exception

class chooseLanguage : BaseActivity(),chooseLanguageAdapter.Callback {

    private var mLanguage:Language? = null
    private var mAdapter: chooseLanguageAdapter?=null
    private var mArrayList:ArrayList<Language?> = ArrayList()
    private var mLayoutManager: LinearLayoutManager? = null
    private var lastSelected = -1
    private lateinit var language: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_language)
    }

    override fun bindViews() {
        Log.d("chooseLanguages","BindViews")

        mLanguage = mPreferanceManager.getLanguage()
        mLayoutManager = LinearLayoutManager(mContext)

        Log.d("chooseLanguages","getLanguage Function")
        mAdapter = chooseLanguageAdapter(mContext, mArrayList, this)

        with(langRecycler){
            addItemDecoration(
                SimpleDividerItemDecoration(
                    mContext,
                    R.drawable.item_separator_filter
                )
            )
            layoutManager = mLayoutManager
            langRecycler.adapter = mAdapter
        }

        applyBtn.setOnClickListener(View.OnClickListener {
//            mPreferanceManager.setLanguage(mArrayList[lastSelected])
//            finish()
            Log.d("chooseLanguages","applyBtn")

            showDialog()
        })

        cancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        callApiServiceList()
    }

    private fun showDialog() {
        Log.d("chooseLanguages","show Dialog")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
        builder.setMessage("You are changing the application’s language. Please note: Only itineraries that are created in the currently selected language will be available.")

        builder.setPositiveButton("Change Language"){dialogInterface, which ->

            Log.d("chooseLanguages","change Language")

            if (lastSelected!=-1 && !mArrayList.isNullOrEmpty())
                Log.d("chooseLanguages","change Language If")
                mPreferanceManager.setLanguage(mArrayList[lastSelected])
            finish()
        }

        builder.setNegativeButton("Cancel"){dialogInterface, which ->
            Log.d("chooseLanguages","Cancel")
        }

        val alertDialog:AlertDialog = builder.create()
  //      alertDialog.setCancelable(false)
        alertDialog.cancel()
        alertDialog.show()
    }

    private fun callApiServiceList(){
        Log.d("chooseLanguages","Call Api")

        val hashMap = HashMap<String, Any?>()
        val serviceManager = ServiceManager(mContext)

        Log.d("chooseLanguages","Service Manager")

        serviceManager.ApiGetLanguages(
            hashMap,
            object : ResponseListener<retrofit2.Response<Response<ArrayList<Language?>>>>(){
                override fun onRequestSuccess(response: retrofit2.Response<Response<ArrayList<Language?>>>) {
                    val responseBody = response.body()
                    Log.d("chooseLanguages","Result1:"+response.body()!!.result)
                    Log.d("chooseLanguages","Result1:"+responseBody!!.result.toString())

                    if (responseBody != null && response.code() == Const.SUCCESS){
                        val mArrayList1 = responseBody.result
                        if (!mArrayList1.isNullOrEmpty() && mLanguage!=null){
                            val index = mArrayList1.indexOf(mLanguage)
                            if (index!=-1){
                                mArrayList1[index]?.isSelected = true
                                lastSelected = index
                            }
                        }
                        setData(mArrayList1)
                    }
                }

                override fun onRequestFailed(t: Throwable) {
                    super.onRequestFailed(t)
                    Log.d("chooseLanguages","Failed")
                }
            }
        )
    }

    private fun setData(mData: java.util.ArrayList<Language?>?) {
        Log.d("chooseLanguages","setData"+mData)

        mArrayList.addAll(mData!!)
        mAdapter!!.notifyDataSetChanged()
        langRecycler.visibility = View.VISIBLE
    }


    override fun onItemClick(position: Int) {
        if (position != -1) {
            val mData = mArrayList[position]
            if (mData != null) {
                //  if (mData.code != "gu" && mData.code != "hi") {
                if (position != -1) {
                    if (lastSelected != position) {

                        val mData = mArrayList[position]
                        if (mData != null) {
                            language = mData.name
                        }
                        if (mData != null) {
                            mData.isSelected = true
                            mAdapter?.notifyItemChanged(position)
                        }
                        if (lastSelected != -1) {
                            val mDataLast = mArrayList[lastSelected]
                            mDataLast?.isSelected = false
                            mAdapter?.notifyItemChanged(lastSelected)
                        }
                    }
                    lastSelected = position
                }
                // }
            }
        }
    }
    companion object {
        fun startActivity(mContext: Context) {
            val intent = Intent(mContext, chooseLanguage::class.java)
            mContext.startActivity(intent)
        }
    }
}