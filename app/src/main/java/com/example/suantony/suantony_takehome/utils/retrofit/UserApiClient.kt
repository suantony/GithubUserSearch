package com.example.suantony.suantony_takehome.utils.retrofit

import com.example.suantony.suantony_takehome.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object UserApiClient {
    fun create(): UserServiceInterface {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
        return retrofit.create(UserServiceInterface::class.java)
    }
}

interface UserServiceInterface {
    @GET("search/users")
    fun getSearchUsersWithPage(
        @Query("q") username: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String
    ): Call<UserResponse>
}