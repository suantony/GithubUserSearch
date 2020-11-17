package com.example.suantony.suantony_takehome.utils.retrofit

import com.example.suantony.suantony_takehome.data.source.remote.response.UserResponse

class UserApiHelper() {
    private val restApiInterface = UserApiClient.create()

    fun getSearchUsersWithPage(
        username: String,
        page: String,
        perPage: String,
        handler: UserApiHandler<UserResponse>
    ) {
        restApiInterface.getSearchUsersWithPage(username, page, perPage).enqueue(handler)
    }
}
