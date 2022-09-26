package com.example.getgithubrepository.model.userdatalist

import androidx.lifecycle.ViewModel
import com.example.getgithubrepository.model.userdata.UserListData

class UserDataListViewModel:ViewModel() {
    private lateinit var userDataList: UserDataList

    fun get(): UserDataList {
        return userDataList
    }

    fun add(value: MutableList<UserListData>) {
        userDataList.items.addAll(value)
    }

    fun initUserDataListParameter(userDataList: UserDataList?) {
        if (userDataList != null) {
            this.userDataList = userDataList
        }
    }
}