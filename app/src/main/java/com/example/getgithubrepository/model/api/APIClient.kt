package com.example.getgithubrepository.model.api

import android.util.Log
import com.example.getgithubrepository.Constants
import com.example.getgithubrepository.R
import com.example.getgithubrepository.model.userdatalist.UserData
import com.example.getgithubrepository.model.userdatalist.UserDataList
import com.example.getgithubrepository.model.userrepo.UserRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.GITHUB_API_ROOT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(GitHubService::class.java)

    fun getUserNameList(userName: String, pageCount: String, callback: (UserDataList) -> Unit) {
        val request = service.getUserNameList(R.string.github_token.toString(), userName, Constants.PER_PAGE_HUNDRED, pageCount)
        request.enqueue(object : Callback<UserDataList> {
            override fun onResponse(
                call: Call<UserDataList>,
                response: Response<UserDataList>
            ) {
                try {
                    if (response.body() != null && response.body()!!.items.isNotEmpty()) {
                        val userDataList = response.body()!!
                        callback(userDataList)
                        Log.d("onResponse", userDataList.toString())

                    }
                } catch (e: IOException) {
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<UserDataList>, t: Throwable) {
                Log.d("@@@@@error",t.toString())
            }
        })
    }

    fun getUserData(userName: String, callback: (UserData) -> Unit) {
        val userRequest = service.getUserData(R.string.github_token.toString(),userName)
        userRequest.enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                try {
                    response.body() ?: return
                    val userData: UserData = response.body()!!
                    callback(userData)
                    Log.d("onResponse", userData.toString())

                } catch (e: IOException) {
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("@@@@@error",t.toString())
            }
        })
    }

    fun getUserReposList(userName: String, pageCount: Int,callback: (List<UserRepo>) -> Unit){
        val repoRequest = service.getUserReposList(R.string.github_token.toString(), userName, Constants.PER_PAGE_HUNDRED, pageCount.toString())
        repoRequest.enqueue(object : Callback<List<UserRepo>> {
            override fun onResponse(
                call: Call<List<UserRepo>>,
                response: Response<List<UserRepo>>
            ) {
                try {
                    if (response.body() != null && response.body()!!.isNotEmpty()) {
                        val userRepoList: List<UserRepo> = response.body() as List<UserRepo>
                        callback(userRepoList)
                        Log.d("onResponse", userRepoList.toString())

                    }
                } catch (e: IOException) {
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<List<UserRepo>>, t: Throwable) {
                Log.d("@@@@@error",t.toString())
            }
        })
    }
}