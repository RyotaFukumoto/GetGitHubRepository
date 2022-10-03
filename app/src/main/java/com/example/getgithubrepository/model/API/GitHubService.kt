package com.example.getgithubrepository.model.API

import com.example.getgithubrepository.model.userdatalist.UserData
import com.example.getgithubrepository.model.userdatalist.UserDataList
import com.example.getgithubrepository.model.userrepo.UserRepo
import retrofit2.Call
import retrofit2.http.*

interface GitHubService {
    @GET("search/users")
    fun getUserNameList(
        @Header("Authorization") accessToken: String,
        @Query("q") userName: String,
        @Query("per_page") perPage: String,
        @Query("page") page: String

    ):Call<UserDataList>

    @GET("users/{user_name}")
    fun getUserData(
        @Header("Authorization") accessToken: String,
        @Path("user_name") userName: String,
    ):Call<UserData>

    @GET("users/{user_name}/repos")
    fun getUserReposList(
        @Header("Authorization") accessToken: String,
        @Path("user_name") userName: String,
        @Query("per_page") perPage: String,
        @Query("page") page: String
    ):Call<List<UserRepo>>
}