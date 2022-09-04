package com.example.getgithubrepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = Request.Builder()
            .url(getString(R.string.whetherApi))
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            // 成功時に呼ばれる
            override fun onResponse(call: Call, response: Response) {
                Log.d("@@@@@debug", response.body!!.string())
            }

            // 失敗時に呼ばれる
            override fun onFailure(call: Call, e: IOException) {
                Log.d("@@@@@debug", e.toString())
            }
        })

    }
}