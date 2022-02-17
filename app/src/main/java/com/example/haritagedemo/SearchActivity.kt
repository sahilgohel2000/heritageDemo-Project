package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.BaseActivity
import com.example.haritagedemo.API.PreferanceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(),SiteNearbyAdapter.OnNearBySiteClickCallback {

    private var mArrayList: ArrayList<FieldNearbySitesLocation?> = ArrayList()
    private var mAdapter: SiteNearbyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
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
            Log.d("SearchActivity","AdapterSet")
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(SpacesItemDecoration(spacingVertical, spacingHorizontal))
            adapter = mAdapter
        }

        cleartext.setOnClickListener(View.OnClickListener {
            Log.d("SearchActivity","clear text")
            mPreferanceManager.clearResentSearch()
            mArrayList.clear()
            recentSearchRecycler.visibility = View.GONE
            recentserach.visibility = View.GONE
            cleartext.visibility = View.GONE
        })

        setViews()
    }

    private fun setViews() {
        Log.d("SearchActivity","setviews")
        ContentManager.init(mContext!!)
        val cityPradiction = findViewById<View>(R.id.searchView) as SearchView
        cityPradiction.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchActivity","return true")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchActivity","return false")
                return true
            }
        })
    }

    override fun onNearBySiteClick(position: Int) {
        TODO("Not yet implemented")
    }
}