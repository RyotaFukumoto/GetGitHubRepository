package com.example.getgithubrepository

data class UserDataList(
    var total_count: Int?,
    var incomplete_results:Boolean?,
    var items: List<UserData>?
)
