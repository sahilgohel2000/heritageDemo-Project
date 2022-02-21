package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.BaseActivity
import com.example.haritagedemo.API.PreferanceManager
import com.example.haritagedemo.API.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    private var mArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mAdapter: SiteNearbyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        cancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun bindViews() {
        val temp = mPreferanceManager.getResentSearch()

        Log.d("SearchActivity","BindView1"+mPreferanceManager.getResentSearch())

        if (!temp.isNullOrEmpty())
            mArrayList.addAll(temp)
        recentserach.visibility = View.GONE.takeIf { mArrayList.isNullOrEmpty() }?: View.VISIBLE

        Log.d("SearchActivity","BindView2"+mArrayList)

        val spacingVertical = resources.getDimensionPixelSize(R.dimen._8dp)
        val spacingHorizontal = resources.getDimensionPixelSize(R.dimen._zero_dp)

        mAdapter = SiteNearbyAdapter(
            mContext,
            mArrayList,
            this
        )
        with(recentSearchRecycler){
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical, spacingHorizontal))
            adapter = mAdapter
        }

        cleartext.setOnClickListener(View.OnClickListener {
            mPreferanceManager.clearResentSearch()
            mArrayList.clear()
            recentSearchRecycler.visibility = View.GONE
            recentserach.visibility = View.GONE
            cleartext.visibility = View.GONE
        })
        setViews()
    }

    private fun setViews() {
        ContentManager.init(mContext!!)
        val cityPradiction = findViewById<View>(R.id.cityPrediction) as AutoCompleteTextView

        cityPradiction.threshold = 2
        cityPradiction.setAdapter(MyAutoCompleteAdapter(this))

        cityPradiction.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val mData = parent.getItemAtPosition(position) as FieldNearbySitesLocation
            cityPrediction.setText("")
            cityPrediction.setSelection(cityPrediction.text.length)
            Util.openDetailsScreen(mContext, mData.type, mData.id)
            addToResentSearch(mData)
        }
        cleartext.visibility = View.GONE.takeIf { mArrayList.isNullOrEmpty() } ?: View.VISIBLE
    }

    private fun addToResentSearch(mData: FieldNearbySitesLocation) {
        val mArrayList = ArrayList<FieldNearbySitesLocation?>()
        val temp = mPreferanceManager.getResentSearch()
        var index = -1

        if (!temp.isNullOrEmpty()) {
            mArrayList.addAll(temp)
            index = temp.indexOf(mData)
        }

        if (!temp.isNullOrEmpty() && index != -1) {
            mArrayList.removeAt(index)
        }

        if (!mArrayList.isNullOrEmpty() && mArrayList.size >= 5) {
            mArrayList.removeAt(mArrayList.size - 1)
        }

        mArrayList.add(0, mData)
        mPreferanceManager.setResentSearch(mArrayList)
        this.mArrayList.clear()
        this.mArrayList.addAll(mArrayList)
        mAdapter?.notifyDataSetChanged()
        recentserach.visibility = View.GONE.takeIf { mArrayList.isNullOrEmpty() } ?: View.VISIBLE
        cleartext.visibility = View.GONE.takeIf { mArrayList.isNullOrEmpty() } ?: View.VISIBLE
        recentSearchRecycler.visibility = View.VISIBLE
    }

    companion object{
        fun startActivity(mContext: Context){
            val intent = Intent(mContext, SearchActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    override fun onNearBySiteClick(position: Int) {
        if (position != -1){
            val mData = mArrayList[position]
            if (mData != null){
                Util.openDetailsScreen(mContext,mData.type,mData.id)
            }
        }
    }
}