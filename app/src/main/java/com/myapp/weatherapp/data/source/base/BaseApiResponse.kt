package com.myapp.weatherapp.data.source.base


import com.myapp.weatherapp.utils.ApiResult
import org.json.JSONObject
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResult.Success(it)
                }
            }

            val errorBody = response.errorBody()?.string()
            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                val errorJson = JSONObject(errorBody)
                errorJson.optString("message", "Something went wrong")
            } else {
                "${response.code()} ${response.message()}"
            }
            return error(errorMessage)
        } catch (ex: Exception) {
            return error(ex.message ?: ex.toString())
        }
    }

    private fun <T> error(errorMessage: String): ApiResult<T> = ApiResult.Error(errorMessage)
}