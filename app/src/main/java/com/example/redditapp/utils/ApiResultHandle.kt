package com.example.redditapp.utils

sealed class ApiResultHandler<out T>(
    val data: T? = null,
    val message: String? = null,
    val code: String? = null
) {
    data class Success<out T>(val value: T) : ApiResultHandler<T>(data = value)
    data class ApiError(val httpCode: String? = null, val error: String) :
        ApiResultHandler<Nothing>(code = httpCode, message = error)
}