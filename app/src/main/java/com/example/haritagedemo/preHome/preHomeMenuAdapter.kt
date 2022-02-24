package com.example.haritagedemo.preHome

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haritagedemo.R
import com.example.haritagedemo.preHome.preHomeActivity.Companion.MENU_ID_HERITAGE_WALKS
import java.util.ArrayList

class preHomeMenuAdapter(
    private val mContext: Context,
    private val height: Int,
    public var mArrayList: ArrayList<preHomeModel>,
    internal var callback: OnDrawerItemClickCallback
):RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    preHomeSubMenuAdapter.OnSubDrawerItemClickCallback{

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(inflater.inflate(R.layout.item_pre_home,parent,false))
    }

    internal inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imgIcon: ImageView = itemView!!.findViewById(R.id.imgView)
        var imgSub: ImageView = itemView!!.findViewById(R.id.imgSub)
        var imgText: TextView = itemView!!.findViewById(R.id.imgText)
        var desc: TextView = itemView!!.findViewById(R.id.imgDesc)
        var menuContainer: LinearLayout = itemView!!.findViewById(R.id.menuContainer)
        var mRelativeLayout: ConstraintLayout = itemView!!.findViewById(R.id.mLayout)
        var mRecyclerView: RecyclerView = itemView!!.findViewById(R.id.recyclerview1)

        init {
            itemView!!.setOnClickListener(View.OnClickListener {
                if (adapterPosition!=-1){
                    if (mArrayList[adapterPosition].subMenu.isNullOrEmpty()){
                        callback.onDrawerItemClick(mArrayList[adapterPosition].menuId)
                    }else{
                        if (lastPosition != -1 && lastPosition != adapterPosition && mArrayList[lastPosition].isOpen){
                            mArrayList[lastPosition].isOpen = !mArrayList[lastPosition].isOpen
                            notifyItemChanged(lastPosition)
                        }
                        mArrayList[adapterPosition].isOpen = !mArrayList[adapterPosition].isOpen
                        notifyItemChanged(adapterPosition)
                        lastPosition = adapterPosition
                    }
                }
            })
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val result = mArrayList[position]
            holder.imgText.text = result.titleResourceId
            holder.imgIcon.setImageResource(result.imageResourceID)
            holder.desc.setText(result.descStringId)
            holder.imgSub.visibility =
                View.GONE.takeIf { result.subMenu.isNullOrEmpty() } ?: View.VISIBLE

            if (result.isOpen) {
                holder.mRecyclerView.visibility = View.VISIBLE
                holder.imgSub.setImageResource(R.drawable.dropdown_epanded_home)
            } else {
                holder.mRecyclerView.visibility = View.GONE
                holder.imgSub.setImageResource(R.drawable.dropdown_home)
            }


            if (!result.subMenu.isNullOrEmpty()) {
                holder.mRecyclerView.layoutManager = LinearLayoutManager(mContext)
                holder.mRecyclerView.adapter =
                    preHomeSubMenuAdapter(mContext, result.subMenu!!, this)
            }

            if (result.isOpen && !result.subMenu.isNullOrEmpty()) {
                holder.menuContainer.layoutParams.height =
                    height + getViewHeight(holder.mRecyclerView)
                holder.imgIcon.layoutParams.height = height + getViewHeight(holder.mRecyclerView)
                holder.mRelativeLayout.layoutParams.height = height
                holder.imgIcon.setImageResource(R.drawable.heritage_walk_selected.takeIf { result.menuId == MENU_ID_HERITAGE_WALKS }
                    ?: R.drawable.tourism_package_selected)
            } else {
                holder.menuContainer.layoutParams.height = height
                holder.mRelativeLayout.layoutParams.height = height
                holder.imgIcon.layoutParams.height = height
                holder.imgIcon.setImageResource(result.imageResourceID)
            }
        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    fun getViewHeight(view: View): Int {
        val wm: WindowManager =
            view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        val deviceWidth: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val size = Point()
            display.getSize(size)
            size.x
        } else {
            display.width
        }
        val widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(widthMeasureSpec, heightMeasureSpec)
        return view.measuredHeight //        view.getMeasuredWidth();
    }

    interface OnDrawerItemClickCallback {
        fun onDrawerItemClick(menuId: Int)
    }

    override fun onSubDrawerItemClick(menuId: Int) {
        callback.onDrawerItemClick(menuId)
    }
}