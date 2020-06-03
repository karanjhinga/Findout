package com.karan.findout.utils

import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    @Throws(Exception::class)
    suspend fun <T : Any> apiRequestWithException(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful)
            return response.body()!!
        else
            throw response.getApiException()
    }

    @Throws(Exception::class)
    fun <T : Any> Response<T>.getApiException(): Exception {
        val error = errorBody()?.string()
        if (error.isNullOrEmpty()) throw ApiException()
        val errorObj = JSONObject(error)
        if (errorObj.getInt("code") == 401) {
            return UnAuthorisedException()
        }
        return ApiException(errorObj.getString("message"))
    }
}
