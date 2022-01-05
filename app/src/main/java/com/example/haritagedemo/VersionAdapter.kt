package com.example.haritagedemo

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class VersionAdapter(val versionList:List<Version>) : RecyclerView.Adapter<VersionAdapter.VersionVH >()
{

    class VersionVH (itemView:View):RecyclerView.ViewHolder(itemView){
        var codeNameText:TextView = itemView.findViewById(R.id.cardName)
        var descriptionText:TextView = itemView.findViewById(R.id.cardDescription)
        var linearLayout:LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandableLayout:RelativeLayout = itemView.findViewById(R.id.expandableView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionVH {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        return VersionVH(view)
    }

    override fun onBindViewHolder(holder: VersionVH, position: Int) {
        val version:Version = versionList[position]
        holder.codeNameText.text = version.codeName
        holder.descriptionText.text = version.description

        val isExpandable:Boolean = versionList[position].expandable

        //check it here in else condition or apply more condition and check
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener(View.OnClickListener {
            val version = versionList[position]
            version.expandable = !version.expandable
            notifyItemChanged(position)
        })
    }

    override fun getItemCount(): Int {
        return versionList.size
    }
}