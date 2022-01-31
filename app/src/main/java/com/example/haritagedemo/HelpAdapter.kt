package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.API.Util
import com.example.haritagedemo.Model.HelpModel
import kotlinx.android.synthetic.main.activity_help.view.*
import kotlinx.android.synthetic.main.items_help.view.*

class HelpAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<HelpModel?>,
    internal var callback: NumberAdapter.Callback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.items_help, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
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
        fun bindViews(mData: HelpModel) {
            itemView.numText.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
            itemView.numText.text = mData.helplineTitle
            Util.loadImageUrl(mContext, mData.icon, null, itemView.imgIcon)
            itemView.mcHelpRecylcer.adapter = NumberAdapter(mContext, mData.helplineNumber, callback)
        }

        init {
            val spacingVertical = mContext.resources.getDimensionPixelSize(R.dimen._zero_dp)
            val spacingHorizontal = mContext.resources.getDimensionPixelSize(R.dimen._zero_dp)

            with(itemView.mcHelpRecylcer) {
                addItemDecoration(
                    SpacesItemDecoration(
                        spacingVertical,
                        spacingHorizontal
                    )
                )
                layoutManager = LinearLayoutManager(mContext)
            }
        }
    }
}