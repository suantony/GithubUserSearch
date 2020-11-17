package com.example.suantony.suantony_takehome.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.suantony.suantony_takehome.data.source.UserDataRepository
import com.example.suantony.suantony_takehome.data.source.local.entity.UserEntity
import com.example.suantony.suantony_takehome.utils.vo.Resource

class UserViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {
    private lateinit var username: String
    private lateinit var page: String
    private lateinit var perPage: String

    fun setData(username: String, page: String, perPage: String) {
        this.username = username
        this.page = page
        this.perPage = perPage
    }

    fun getSearchUsersWithPage(): LiveData<Resource<List<UserEntity>>> =
        userDataRepository.getSearchUsersWithPage(username, page, perPage)
}