package com.example.haritagedemo.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.R
import kotlinx.android.synthetic.main.item_help.view.*
import kotlinx.android.synthetic.main.item_helpline_number.view.*
import java.util.ArrayList

class HelpNumAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<String>,
    internal var callback: Callback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_help,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HelpNumAdapter.MyViewHolder){
            val mData = mArrayList[1]
            if (mData != null){
                holder.bindViews(mData)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    internal inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindViews(mData: String){
            itemView.txtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.accent))
            itemView.txtTitle.text = mData
        }

        init {
            itemView.setOnClickListener{
                callback.onHelpClick(mArrayList[adapterPosition])
            }
        }
    }

    interface Callback{
        fun onHelpClick(mNumber: String)
    }
}