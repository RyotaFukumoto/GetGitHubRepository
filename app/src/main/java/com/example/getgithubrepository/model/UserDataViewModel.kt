package com.example.getgithubrepository.model

import androidx.lifecycle.ViewModel

class UserDataViewModel: ViewModel() {
    lateinit var userData: UserData
    lateinit var userDataList: UserDataList
    fun initUserDataParameter(userData: UserData) {
        this.userData = userData
    }

    fun initUserDataListParameter(userDataList: UserDataList) {
        this.userDataList = userDataList
    }
}