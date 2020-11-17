package com.example.suantony.suantony_takehome.di

import com.example.suantony.suantony_takehome.data.source.UserDataRepository
import com.example.suantony.suantony_takehome.data.source.remote.RemoteDateSource
import com.example.suantony.suantony_takehome.utils.retrofit.UserApiHelper

object Injection {
    fun provideRepository(): UserDataRepository {
        val remoteDateSource = RemoteDateSource.getInstance(UserApiHelper())

        return UserDataRepository.getInstance(remoteDateSource)
    }
}