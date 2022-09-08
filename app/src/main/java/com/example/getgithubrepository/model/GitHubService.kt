package com.example.getgithubrepository.model

import com.example.getgithubrepository.model.UserDataList
import retrofit2.Call
import retrofit2.http.*

interface GitHubService {
    @GET("search/users")
    fun getUserNameList(@Query("q") userName: String):Call<UserDataList>
}