package com.example.suantony.suantony_takehome.data.source.remote

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)

        fun <T> empty(msg: String): ApiResponse<T> = ApiResponse(StatusResponse.EMPTY, null, msg)

        fun <T> error(msg: String): ApiResponse<T> = ApiResponse(StatusResponse.ERROR, null, msg)
    }
}