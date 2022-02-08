package com.example.haritagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_privacy_policy.*

class Privacy_policyActivity : AppCompatActivity() {

    val url = "http://183.87.214.73:8080/heritage/aha-privacy-policy.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        agreeBtn.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()
            finish()
        })
        setUpWebView()
    }


    private fun setUpWebView() {

        p_policy.settings.allowFileAccess = true
        p_policy.settings.allowContentAccess = true
        p_policy.settings.javaScriptEnabled = true
        p_policy.settings.loadWithOverviewMode = true
        p_policy.settings.useWideViewPort = true

        p_policy.webViewClient = object:WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null){
                    view!!.loadUrl(url)
                }
                return true
            }
        }

        p_policy.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        p_policy.loadUrl(url)
    }
}