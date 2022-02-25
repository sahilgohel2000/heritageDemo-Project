package com.example.haritagedemo.preHome

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haritagedemo.*
import com.example.haritagedemo.API.BaseActivity
import com.example.haritagedemo.API.Const
import com.example.haritagedemo.Activity.AboutAhmedabadActivity
import com.example.haritagedemo.Activity.HeritageWalkActivity
import com.example.haritagedemo.Model.TourPackageModel
import kotlinx.android.synthetic.main.activity_pre_home.*
import java.lang.Exception

class preHomeActivity : BaseActivity(),preHomeMenuAdapter.OnDrawerItemClickCallback {

    private var mAdapter: preHomeMenuAdapter? = null
    private val mMenuList: ArrayList<preHomeModel> = ArrayList()
    private val mSubMenuList1: ArrayList<navigationDrawerModel?> = ArrayList()
    private val mSubMenuList2: ArrayList<navigationDrawerModel?> = ArrayList()

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_home)
    }

    override fun bindViews() {
        mPreferanceManager.setPreHome(true)
        setUpMenu()
        mainRecycler.layoutManager = LinearLayoutManager(mContext)
        val spacing = 10
        mainRecycler.addItemDecoration(SpacesItemDecoration(spacing, spacing))
        val vto = mainRecycler.viewTreeObserver

        vto.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mainRecycler.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    mainRecycler.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                var height = mainRecycler.measuredHeight / mMenuList.size
                height -= (spacing + spacing / mMenuList.size + 1)
                mAdapter = preHomeMenuAdapter(mContext, height, mMenuList, this@preHomeActivity)
                mainRecycler.adapter = mAdapter
            }
        })

        txtLetsGo.setOnClickListener(View.OnClickListener {
            val intent:Intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setUpMenu() {
        mMenuList.add(
            preHomeModel(
                MENU_ID_EXP_AHMEDABAD,
                "Explore Ahmedabad",
                R.string.explore_ahm_desc,
                R.drawable.explore_ahmedabad_bg,null
            )
        )

        mSubMenuList1.add(
            navigationDrawerModel(
                MENU_ID_MORNING_HERITAGE_WALKS,
                R.string.morning_heritage_walk,
                0,null
            )
        )
        mSubMenuList1.add(
            navigationDrawerModel(
                MENU_ID_EVENING_HERITAGE_WALKS,
                R.string.evening_heritage_walk,
                0,null
            )
        )

        mMenuList.add(
            preHomeModel(
                MENU_ID_HERITAGE_WALKS,getString(R.string.heritage_walk),
                R.string.heritage_walk_desc,
                R.drawable.heritage_walks_bg, mSubMenuList1
            )
        )
        mSubMenuList2.add(
            navigationDrawerModel(
                MENU_ID_AMC_TOURISM_PACKAGES,
                R.string.amc_tourism,
                0,null
            )
        )

        mMenuList.add(
            preHomeModel(
                MENU_ID_TOURISM_PACKAGES,
                getString(R.string.tourism_package),
                R.string.tourism_packages_desc,
                R.drawable.tourism_package_bg,mSubMenuList2
            )
        )

        mMenuList.add(
            preHomeModel(
                MENU_ID_ABOUT_AHMEDABAD,
                getString(R.string.ageless_ahm),
                R.string.heritage_desc,
                R.drawable.about_ahmedabad_bg,null
            )
        )
    }

    override fun onDrawerItemClick(menuId: Int) {
        when(menuId){
            MENU_ID_HOME -> {
                HomeActivity.startActivity(mContext)
            }
            MENU_ID_EXP_AHMEDABAD -> {
//                HomeActivity.startActivity(mContext)
                val intent:Intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
//                finish()
            }
            MENU_ID_ABOUT_AHMEDABAD -> {
                AboutAhmedabadActivity.startActivity(mContext)
            }
            MENU_ID_MORNING_HERITAGE_WALKS -> {
                HeritageWalkActivity.startActivity(mContext, Const.HERITAGEWALK.MORNING)
            }
            MENU_ID_EVENING_HERITAGE_WALKS -> {
                HeritageWalkActivity.startActivity(mContext, Const.HERITAGEWALK.EVENING)
            }
            MENU_ID_AMC_TOURISM_PACKAGES -> {
                TourismPackageActivity.startActivity(mContext, Const.TOURISMPACKAGE.AMC)
            }
            MENU_ID_OTHER_TOURISM_PACKAGES -> {
                TourismPackageActivity.startActivity(mContext, Const.TOURISMPACKAGE.OTHER)
            }
        }

    }

    companion object {
        const val MENU_ID_HOME = 1
        const val MENU_ID_EXP_AHMEDABAD = 2
        const val MENU_ID_HERITAGE_WALKS = 3
        const val MENU_ID_TOURISM_PACKAGES = 4
        const val MENU_ID_ABOUT_AHMEDABAD = 5
        const val MENU_ID_MORNING_HERITAGE_WALKS = 6
        const val MENU_ID_EVENING_HERITAGE_WALKS = 7
        const val MENU_ID_AMC_TOURISM_PACKAGES = 8
        const val MENU_ID_OTHER_TOURISM_PACKAGES = 9

        fun startActivity(mContext: Context) {
            val intent = Intent(mContext, preHomeActivity::class.java)
            mContext.startActivity(intent)
        }
    }

}

