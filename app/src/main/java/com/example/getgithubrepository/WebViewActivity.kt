package com.example.getgithubrepository

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.getSettings().setJavaScriptEnabled(true)

        webView.loadUrl("https://google.com")
        webView.requestFocus()
    }
}