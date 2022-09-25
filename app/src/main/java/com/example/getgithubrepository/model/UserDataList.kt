package com.example.getgithubrepository.model

data class UserDataList(
    var total_count: Int,
    var incomplete_results:Boolean,
    var items: MutableList<UserListData>
)
