package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_nearby_sites.view.*
import java.util.ArrayList

class SiteNearbyAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<FieldNearbySitesLocation?>,
    internal var callback: OnNearBySiteClickCallback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_nearby_sites,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            val mData = mArrayList[position]
            if (mData != null) {
                holder.bindViews(mData)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(mData: FieldNearbySitesLocation) {
            itemView.txtSiteName.text = mData.siteName
            itemView.txtSiteType.text = mData.category
        }

        init {
            itemView.setOnClickListener {
                callback.onNearBySiteClick(adapterPosition)
            }
        }
    }

    interface OnNearBySiteClickCallback {
        fun onNearBySiteClick(position: Int)
    }

}