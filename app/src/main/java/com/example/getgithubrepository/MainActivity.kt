package com.example.getgithubrepository

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.getgithubrepository.databinding.ActivityMainBinding
import com.google.firebase.database.core.Repo
import okhttp3.*

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import okhttp3.Call
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
    }

    override fun onClick(v: View?) {

        val userNameTextView = binding.userNemeText
        val text = userNameTextView.text.toString()
        val request = service.getUserNameList(text)

        request.enqueue(object : Callback<UserDataList> {
            override fun onResponse(
                call: retrofit2.Call<UserDataList>,
                response: Response<UserDataList>
            ) {
                try{
                    val arr: Response<UserDataList> = response
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