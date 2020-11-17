package com.example.suantony.suantony_takehome.data.source

import androidx.lifecycle.LiveData
import com.example.suantony.suantony_takehome.data.source.local.entity.UserEntity
import com.example.suantony.suantony_takehome.utils.vo.Resource

interface UserDataSource {
    fun getSearchUsersWithPage(
        username: String,
        page: String,
        perPage: String
    ): LiveData<Resource<List<UserEntity>>>
}