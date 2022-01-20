package com.example.haritagedemo

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.graphics.blue
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_faqactivity.*
import kotlinx.android.synthetic.main.row.*
import java.util.*
import kotlin.collections.ArrayList

class FAQActivity : AppCompatActivity() {

    val versionList = ArrayList<Version>()
    private var lastPosition = -1
    var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqactivity)
        initData()
        setRecyclerView()

        arrowBack.setOnClickListener(View.OnClickListener {
            val intent:Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setRecyclerView() {
        val versionAdapter = VersionAdapter(versionList)
        recyclerView.adapter = versionAdapter
        recyclerView.setHasFixedSize(true)
    }

    private fun initData() {
        versionList.add(Version(
            "Where is the Haritage Department Located?",
            "It is Located in A-block Ahmedabad Municipal Corporation, Danapith."
        ))
        versionList.add(Version(
            "Where does Haritage Walk start from?",
            "It starts at 7:45 am from Swaminarayan Temple, Kalupur.",
        ))
        versionList.add(Version(
            "How many times a day Haritage Walk?",
            "Twice a day - Morning Walk and Evening Walk.",
        ))
        versionList.add(Version(
            "Where can I find Information Kiosks in?",
            "You Can Find Kiosks in Five Places - Rivorfront, Gujarat University, Nehrunagar, Railway station, Airport.",
        ))
        versionList.add(Version(
            "Is there any Goverment Mandated Hotels?",
            "You can opt for staying at Toran Hotels, Opposite Gandhi Ashram, Shahibaugh",
        ))
        versionList.add(Version(
            "When Does International Kite Festival?",
            "The Kite Festival happens every year 13-14th January.",
        ))
        versionList.add(Version(
            "Does Ahmedabad have good hotels to stay?",
            "Yes, Ahemedabad has State-of-art hotels like Marriot, Hyatt, Fortune Hotels.",
        ))
        versionList.add(Version(
            "Is there a tour guide available for a Haritage?",
            "Yes, a Tour guide is available for haritage walk.",
        ))
        versionList.add(Version(
            "How long does a Haritage Walk last?",
            "It lasts for around 2 hours.",
        ))
        versionList.add(Version(
            "Can I contributr to enrich the content?",
            "You can Write Feedback or Suggestion on awhctrust@gmail.com.",
        ))
    }
}

