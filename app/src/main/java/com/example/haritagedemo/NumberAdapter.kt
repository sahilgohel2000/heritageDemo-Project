package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.number_list.view.*

class NumberAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<String>,
    internal var callback: Callback
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.number_list, parent, false))
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
        fun bindViews(mData: String) {
            itemView.textTitleFirst.setTextColor(ContextCompat.getColor(mContext, R.color.gray))
            itemView.textTitleFirst.text = mData
        }

        init {
            itemView.setOnClickListener {
                callback.onHelpClick(mArrayList[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onHelpClick(mNumber: String)
    }

}