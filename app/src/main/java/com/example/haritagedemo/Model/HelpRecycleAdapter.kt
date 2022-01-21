package com.example.haritagedemo.Model

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.R

class HelpRecycleAdapter<T>(
    private val mContext: Context,
    private val errorLayout: View?,
    val arrayList: ArrayList<T?>?,
    private val recyclerView: RecyclerView,
    private val progress: View?,
    private val callBack: RecyclerAdapterHelperCallback
) {
    private var errTitle: TextView? = null
    private var errMessage: TextView? = null
    private var errbutton: Button? = null

    init {
        if (errorLayout != null) {
            errTitle = errorLayout.findViewById(R.id.numTitle)

            errbutton?.setOnClickListener {
                errorLayout.visibility = View.GONE
                callBack.onRetryPage()
            }
        }
    }

    fun resetValues() {
        arrayList?.clear()
    }

    fun setSuccessResponse(
        isSuccess: Boolean,
        arrayList1: List<T>?,
        message: String,
        shouldShowAddItinerary: Boolean = false
    ) {

        if (isSuccess && !arrayList1.isNullOrEmpty()) {
            handleErrorView(View.GONE, message, View.GONE, View.GONE)
            arrayList?.clear()
            arrayList?.addAll(arrayList1)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        else {
            handleErrorView(View.VISIBLE, message, View.GONE, View.GONE)
        }

    }

    private fun handleErrorView(
        visibility: Int,
        message: String,
        btnVisibility: Int,
        titleVisibility: Int
    ) {
        if (errorLayout != null) {
            errorLayout.visibility = visibility
            errMessage?.text = message
            errbutton?.visibility = btnVisibility
            errTitle?.visibility = titleVisibility
        }
    }

    interface RecyclerAdapterHelperCallback {
        fun onRetryPage()
    }

}