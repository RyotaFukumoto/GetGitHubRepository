package com.example.getgithubrepository.model.userdatalist

import com.example.getgithubrepository.model.userdata.UserListData

data class UserDataList(
    var total_count: Int,
    var incomplete_results:Boolean,
    var items: MutableList<UserListData>
)
