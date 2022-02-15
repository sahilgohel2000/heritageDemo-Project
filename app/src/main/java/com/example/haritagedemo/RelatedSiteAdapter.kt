package com.example.haritagedemo

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.Model.WebsiteLinkModel
import kotlinx.android.synthetic.main.website_items.view.*
import java.util.ArrayList

class RelatedSiteAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<WebsiteLinkModel?>,
    internal var callback: Callback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     return MyViewHolder(inflater.inflate(R.layout.website_items,parent,false))
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

    internal inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindViews(mData:WebsiteLinkModel){
            val data1 = "<u><font color='#244AFC'>${mData.name}</font></u>"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                itemView.websites.text = Html.fromHtml(
                    data1,
                    Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
                )
            }else{
                itemView.websites.text = Html.fromHtml(data1)
            }
        }
        init {
            itemView.setOnClickListener {
                callback.onItemClick(mArrayList[adapterPosition]!!)
            }
        }
    }
    interface Callback {
        fun onItemClick(mData: WebsiteLinkModel)
    }
}

