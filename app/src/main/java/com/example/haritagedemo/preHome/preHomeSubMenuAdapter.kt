package com.example.haritagedemo.preHome

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.R
import com.example.haritagedemo.chooseLanguageAdapter
import kotlinx.android.synthetic.main.item_pre_home_sub.view.*
import java.util.ArrayList

class preHomeSubMenuAdapter(
    private val mContext: Context,
    private var arrayList: ArrayList<navigationDrawerModel?>,
    internal var callback: OnSubDrawerItemClickCallback
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_pre_home_sub,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            val result = arrayList[position]
            holder.divider.visibility = View.VISIBLE.takeIf { position==1 }?:View.GONE
            holder.txtTitle.setText(result!!.titleResourceId)
            holder.imgIcon.visibility = View.INVISIBLE
            holder.imgSub.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    internal inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var txtTitle:TextView = itemView!!.findViewById(R.id.textTitles)
        var imgIcon:ImageView = itemView!!.findViewById(R.id.subImg)
        var imgSub:ImageView = itemView!!.findViewById(R.id.ImgSub)
        var divider:View = itemView!!.findViewById(R.id.Divider)

        init {
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
            itemView!!.recyclerView2.visibility = View.VISIBLE
            itemView.setOnClickListener(View.OnClickListener {
                callback.onSubDrawerItemClick(arrayList[adapterPosition]!!.menuId)
            })
        }
    }

    interface OnSubDrawerItemClickCallback {
        fun onSubDrawerItemClick(menuId: Int)
    }

}


