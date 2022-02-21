package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_choose_layout.view.*
import java.util.ArrayList
import java.util.zip.Inflater
import javax.security.auth.callback.Callback

class chooseLanguageAdapter (
    private val mContext: Context,
    private val mArrayList: ArrayList<Language?>,
    private val callback: Callback
        ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater:LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_choose_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            if (position!=-1){
                val mData = mArrayList[position]
                if (mData != null)
                    holder.bindViews(mData)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    internal inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindViews(mData:Language){
            itemView.txtCountryName.text = mData.name
            itemView.imgFlags.visibility = View.GONE

            itemView.imgSelection.setImageDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.filter_selected.takeIf { mData.isSelected }?:R.drawable.filter_unchecked
                )
            )

            itemView.mParent.setBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    android.R.color.transparent
                )
            )
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