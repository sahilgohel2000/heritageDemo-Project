package com.example.haritagedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.haritagedemo.API.Util
import java.lang.Exception

class CustomPagerAdapter(
    internal var mContext:Context,
    internal var mArrayList: ArrayList<String>
):PagerAdapter() {

    private var mLayoutInflater:LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return mArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.item_site_image_pager,container,false)
        val imageView = itemView.findViewById<View>(R.id.iv_background) as ImageView

        try {
            Util.loadImageUrl(
                mContext,
                mArrayList[position],
                R.drawable.image_slider,
                imageView
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}