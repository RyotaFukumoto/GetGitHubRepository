package com.example.getgithubrepository.model.userrepo

import androidx.lifecycle.ViewModel

class UserRepoViewModel: ViewModel() {
    private lateinit var userRepoList: MutableList<UserRepo>

    fun add(value: MutableList<UserRepo>) {
        this.userRepoList.addAll(value)
    }

    fun  get(): MutableList<UserRepo> {
        return userRepoList
    }

    fun initUserRepoParameter(userRepoList: List<UserRepo>) {
        this.userRepoList = userRepoList as MutableList<UserRepo>
    }
}