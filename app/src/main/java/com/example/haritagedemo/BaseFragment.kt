package com.example.haritagedemo

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haritagedemo.API.PreferanceManager
import java.math.BigDecimal


abstract class BaseFragment : Fragment() {

    var mContext: Context? = null
    lateinit var mPreferenceManager: PreferanceManager
    val TAG = this.javaClass.simpleName
    var mView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPreferenceManager = PreferanceManager(mContext!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView=view
        bindViews(view)
    }

    abstract fun bindViews(view: View)

}