package com.example.suantony.suantony_takehome.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.suantony.suantony_takehome.data.source.remote.response.UserItemResponse
import com.example.suantony.suantony_takehome.data.source.remote.response.UserResponse
import com.example.suantony.suantony_takehome.utils.retrofit.UserApiHandler
import com.example.suantony.suantony_takehome.utils.retrofit.UserApiHelper

class RemoteDateSource private constructor(private val restApi: UserApiHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDateSource? = null

        fun getInstance(userApiHelper: UserApiHelper): RemoteDateSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDateSource(userApiHelper)
            }
    }

    fun getSearchUsersWithPage(
        username: String,
        page: String,
        perPage: String
    ): LiveData<ApiResponse<List<UserItemResponse>>> {
        val resultUsers = MutableLiveData<ApiResponse<List<UserItemResponse>>>()

        restApi.getSearchUsersWithPage(
            username,
            page,
            perPage,
            object : UserApiHandler<UserResponse>() {
                override fun onRequestSuccess(data: UserResponse?) {
                    val userList = ArrayList<UserItemResponse>()
                    if (data?.items?.size != 0) {
                        data?.items?.map {
                            if (it != null) {
                                userList.add(it)
                            }
                        }
                        resultUsers.value = ApiResponse.success(userList)
                    } else {
                        resultUsers.value = ApiResponse.empty("Data not found")
                    }
                }

                override fun onRequestError(errorCode: Int, errorMessage: String) {
                    Log.d("requestError", "errorCode: ${errorCode} & errorMessage: ${errorMessage}")
                    var message = ""
                    if (errorCode == 403) {
                        message = "Rate limit exceeded, please wait a while"
                    } else {
                        message = errorMessage
                    }
                    resultUsers.value = ApiResponse.error(message)
                }

                override fun onNetworkFailure(throwable: Throwable) {
                    Log.d("requestError", "${throwable.message}")
                    resultUsers.value = ApiResponse.error("Network unstable")
                }
            })
        return resultUsers
    }
}