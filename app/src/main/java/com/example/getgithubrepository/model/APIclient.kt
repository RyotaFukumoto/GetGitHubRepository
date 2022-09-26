package com.example.getgithubrepository.model

import android.util.Log
import com.example.getgithubrepository.Constants
import com.example.getgithubrepository.model.userdata.UserListData
import com.example.getgithubrepository.model.userdatalist.UserData
import com.example.getgithubrepository.model.userdatalist.UserDataList
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.coroutines.suspendCoroutine

class APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.GITHUB_API_ROOT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)
    private var responseUserDataList: UserDataList? = null

    fun getUserNameList(userName: String, pageCount: String, callback: (UserDataList?) -> Unit) {
        val request = service.getUserNameList("",userName,"100",pageCount)
        request.enqueue(object : Callback<UserDataList> {
            override fun onResponse(
                call: Call<UserDataList>,
                response: Response<UserDataList>
            ) {
                try {
                    if (response.body() != null && response.body()!!.items.isNotEmpty()) {
                        Log.d("onResponse", response.body().toString())
                        responseUserDataList = response.body()!!
                        callback(responseUserDataList)
                    }
                } catch (e: IOException) {
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<UserDataList>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}