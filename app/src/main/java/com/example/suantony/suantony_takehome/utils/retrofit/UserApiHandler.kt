package com.example.suantony.suantony_takehome.utils.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class UserApiHandler<T> : Callback<T> {

    override fun onResponse(callback: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onRequestSuccess(response.body())
        } else {
            onRequestError(response.code(), response.message())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onNetworkFailure(t)
    }

    abstract fun onRequestSuccess(data: T?)
    abstract fun onRequestError(errorCode: Int, errorMessage: String)
    abstract fun onNetworkFailure(throwable: Throwable)
}