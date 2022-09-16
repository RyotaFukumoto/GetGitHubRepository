package com.example.getgithubrepository.model

import androidx.lifecycle.ViewModel

class UserDataViewModel: ViewModel() {
    lateinit var userData: UserData

    fun initUserDataParameter(userData: UserData) {
        this.userData = userData
    }
}