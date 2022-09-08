package com.example.getgithubrepository

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.getgithubrepository.databinding.ActivityMainBinding
import com.example.getgithubrepository.model.GitHubService
import com.example.getgithubrepository.model.UserDataList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {

        val userNameTextView = binding.userNemeText
        val text = userNameTextView.text.toString()
        // 通信用のクラスに分ける

        val request = service.getUserNameList(text)
        request.enqueue(object : Callback<UserDataList> {
            override fun onResponse(
                call: retrofit2.Call<UserDataList>,
                response: Response<UserDataList>
            ) {
                try{
                    val arr: UserDataList? = response.body()
                    Log.d("onResponse", arr.toString())
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: retrofit2.Call<UserDataList>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}