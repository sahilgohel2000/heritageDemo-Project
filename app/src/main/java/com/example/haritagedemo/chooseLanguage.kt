package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.API.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_language.*

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
        mLanguage = mPreferanceManager.getLanguage()
        mLayoutManager = LinearLayoutManager(mContext)
        with(langRecycler){
            addItemDecoration(
                SimpleDividerItemDecoration(
                    mContext,
                    R.drawable.item_separator_filter
                )
            )
            layoutManager = mLayoutManager
        }

        applyBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        cancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}