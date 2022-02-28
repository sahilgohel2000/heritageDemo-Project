package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.Model.RelatedLinkModel
import com.example.haritagedemo.Model.WebsiteLinkModel
import kotlinx.android.synthetic.main.item_related_link.view.*
import java.util.ArrayList

class RelatedLinkAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<RelatedLinkModel?>,
    internal var callback: CallBack
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_related_link,parent,false))
    }

    internal inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        fun bindView(mData: RelatedLinkModel){
            itemView.linkText.text = mData.name
        }
        init {
            itemView!!.setOnClickListener(View.OnClickListener {
                callback.OnRelatedLink(mArrayList[adapterPosition]!!)
            })
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            val mData = mArrayList[position]
            if (mData != null){
                holder.bindView(mData)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }


    interface CallBack{
        fun OnRelatedLink(pos:RelatedLinkModel)
    }

}