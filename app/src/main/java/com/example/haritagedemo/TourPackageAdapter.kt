package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.Model.TourPackageModel
import kotlinx.android.synthetic.main.item_walk.view.*

class TourPackageAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<TourPackageModel?>,
    internal var callback: Callback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return MyViewHolder(inflater.inflate(R.layout.item_walk,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            val mData = mArrayList[position]
            if (mData != null){
                holder.bindViews(mData)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindViews(mData: com.example.haritagedemo.Model.TourPackageModel?){
            itemView.Text.text = mData!!.name
        }
        init {
            itemView.setOnClickListener{
                callback.onItemClick(adapterPosition)
            }
        }
    }

    interface Callback {
        fun onItemClick(position: Int)
    }
}