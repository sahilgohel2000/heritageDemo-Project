package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.API.Util
import kotlinx.android.synthetic.main.amenties_list_item.view.*
import java.util.ArrayList

class AmentiesAdapter(
    private val mContext: Context,
    private var mArrayList: ArrayList<String?>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.amenties_list_item,parent,false))
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindViews(result: String) {
            Util.loadImageUrl(mContext, result, R.drawable.atm, itemView.imgAmentiees)
        }

        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val result = mArrayList[position]
            if (result != null) {
                holder.bindViews(result)
            }
        }
    }

    override fun getItemCount(): Int {
    return mArrayList.size
    }
}