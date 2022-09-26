package com.example.getgithubrepository.model.userdata

import androidx.lifecycle.ViewModel

class UserDataViewModel: ViewModel() {
    private lateinit var userListData: UserListData

    fun get(): UserListData {
        return userListData
    }

    fun initUserDataParameter(userListData: UserListData) {
        this.userListData = userListData
    }
}