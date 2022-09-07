package com.example.getgithubrepository

import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface GitHubService {
    @GET("search/users")
    fun getUserNameList(@Query("q") userName: String):Call<UserDataList>
}