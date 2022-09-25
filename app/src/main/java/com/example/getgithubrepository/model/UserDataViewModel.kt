package com.example.getgithubrepository.model

import androidx.lifecycle.ViewModel

class UserDataViewModel: ViewModel() {
    lateinit var userListData: UserListData

    fun initUserDataParameter(userListData: UserListData) {
        this.userListData = userListData
    }
}