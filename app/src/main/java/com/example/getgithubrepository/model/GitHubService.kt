package com.example.getgithubrepository.model

import retrofit2.Call
import retrofit2.http.*

interface GitHubService {
    @GET("search/users")
    fun getUserNameList(@Query("q") userName: String):Call<UserDataList>

    @GET("users/{user_name}/repos")
    fun getUserReposList(@Path("user_name") userName: String):Call<List<UserRepo>>
}