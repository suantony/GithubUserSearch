package com.example.suantony.suantony_takehome.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.suantony.suantony_takehome.data.source.local.entity.UserEntity
import com.example.suantony.suantony_takehome.data.source.remote.RemoteDateSource
import com.example.suantony.suantony_takehome.data.source.remote.StatusResponse
import com.example.suantony.suantony_takehome.utils.vo.Resource

class UserDataRepository private constructor(private val remoteDateSource: RemoteDateSource) :
    UserDataSource {
    companion object {
        @Volatile
        private var instance: UserDataRepository? = null

        fun getInstance(remoteDateSource: RemoteDateSource): UserDataRepository =
            instance ?: synchronized(this) {
                instance ?: UserDataRepository(remoteDateSource)
            }
    }

    override fun getSearchUsersWithPage(
        username: String,
        page: String,
        perPage: String
    ): LiveData<Resource<List<UserEntity>>> {
        val userResult = MediatorLiveData<Resource<List<UserEntity>>>()
        val remoteResult = remoteDateSource.getSearchUsersWithPage(username, page, perPage)
        userResult.value = Resource.loading()

        userResult.addSource(remoteResult) { response ->
            val userList = ArrayList<UserEntity>()

            response.body?.map { res ->
                val user = UserEntity(res.login, res.avatarUrl)
                userList.add(user)
            }

            when (response.status) {
                StatusResponse.SUCCESS -> {
                    userResult.value = Resource.success(userList)
                }

                StatusResponse.EMPTY -> {
                    userResult.value = Resource.empty(response.message)
                }

                StatusResponse.ERROR -> {
                    userResult.value = Resource.error(response.message)
                }
            }
        }
        return userResult
    }
}