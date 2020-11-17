package com.example.suantony.suantony_takehome.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: List<UserItemResponse?>? = null
)

data class UserItemResponse(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
)
