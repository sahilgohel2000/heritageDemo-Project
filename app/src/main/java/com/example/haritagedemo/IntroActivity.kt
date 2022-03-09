package com.example.haritagedemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.haritagedemo.API.BaseActivity
import com.example.haritagedemo.Model.IntroModel
import com.example.haritagedemo.preHome.preHomeActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {

    val introList: ArrayList<IntroModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Log.d("IntroActivity","OnCreate")
    }

    override fun bindViews() {
        Log.d("IntroActivity","bindViews")

        introList.add(
            IntroModel(
                R.string.explore_india_first_unesco_city,
                R.drawable.intro_bg_header_01,
                R.drawable.intro_lineart_01,
                R.string.intro_page1
                )
        )
        introList.add(
            IntroModel(
                R.string.explore_india_first_unesco_city,
                R.drawable.intro_bg_header_02,
                R.drawable.intro_lineart_02,
                R.string.intro_page2
                )
        )
        introList.add(
            IntroModel(
                R.string.explore_india_first_unesco_city,
                R.drawable.intro_bg_header_03,
                R.drawable.intro_lineart_03,
                R.string.intro_page3
            )
        )
        introList.add(
            IntroModel(
                R.string.explore_india_first_unesco_city,
                R.drawable.intro_bg_header_04,
                R.drawable.intro_lineart_04,
                R.string.intro_page4
            )
        )
        introList.add(
            IntroModel(
                R.string.explore_india_first_unesco_city,
                R.drawable.intro_bg_header_05,
                R.drawable.intro_lineart_05,
                R.string.intro_page5
            )
        )
        Log.d("IntroActivity","Data Added in Adapter 1")

        val vAdapter = MoviesPagerAdapter(supportFragmentManager, introList)
        introVP.adapter = vAdapter
        Log.d("IntroActivity","Data Added in Adapter 2")

        tabLayouts!!.addTab(tabLayouts!!.newTab())
        tabLayouts!!.addTab(tabLayouts!!.newTab())
        tabLayouts!!.addTab(tabLayouts!!.newTab())
        tabLayouts!!.addTab(tabLayouts!!.newTab())
        tabLayouts!!.addTab(tabLayouts!!.newTab())
        Log.d("IntroActivity","Tab Layout")

        introVP.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayouts))
        tabLayouts.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                nextButton.visibility = View.VISIBLE.takeIf { tab!!.position == 4 }?: View.GONE
                //skipButton.visibility = View.GONE
                Log.d("IntroActivity","Next Button")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        tabLayouts.touchables.forEach{ it.isEnabled = false}

        nextButton.setOnClickListener(View.OnClickListener {
            if (introVP.currentItem == 4){
                preHomeActivity.startActivity(mContext)
                finishAffinity()
            }else{
                introVP.currentItem = introVP.currentItem + 1
            }
        })
        skipButton.setOnClickListener(View.OnClickListener {
            preHomeActivity.startActivity(mContext)
            finishAffinity()
        })
    }

    companion object{
        fun startActivity(mContext: Context){
            val intentr = Intent(mContext, IntroActivity::class.java)
            mContext.startActivity(intentr)
            Log.d("IntroActivity","Start Intro Activity")
        }
    }

    class MoviesPagerAdapter(
        fragmentManager: FragmentManager,
        private val intro: ArrayList<IntroModel>
    ):FragmentStatePagerAdapter(fragmentManager){
        override fun getCount(): Int {
            return intro.size
            Log.d("IntroActivity","Get Count")
        }

        override fun getItem(position: Int): Fragment {
            return IntroFragment.newInstance(intro[position])
            Log.d("IntroActivity","get Item")
        }

    }
}
