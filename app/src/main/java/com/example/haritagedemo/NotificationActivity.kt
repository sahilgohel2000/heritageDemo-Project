package com.example.haritagedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*

class NotificationActivity : AppCompatActivity() {
    
    lateinit var webView2:WebView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        
        webView2 = findViewById(R.id.webView2)
        
        try {
            setUpMapAgain()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpMapAgain() {
        webView2.settings.javaScriptEnabled = true
        webView2.settings.allowContentAccess = true
        webView2.settings.allowFileAccess = true
        webView2.settings.domStorageEnabled = true
        webView2.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        
        webView2.loadUrl(
            "https://www.geeksforgeeks.org/"
        )
        
        webView2.webChromeClient = object :WebChromeClient(){
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        webView2.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
    }
}