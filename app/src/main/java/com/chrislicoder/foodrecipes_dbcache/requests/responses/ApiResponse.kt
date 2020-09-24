package com.chrislicoder.foodrecipes_dbcache.requests.responses

import retrofit2.Response

sealed class ApiResponse<T> {
    class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
    class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
    class ApiEmptyResponse<T> : ApiResponse<T>()

    open fun create(error: Throwable): ApiResponse<T> {
        return ApiErrorResponse(
            with(error.message) {
                this ?: "Unknown error\n" +
                    "Check network connection"
            }
        )
    }

    open fun create(response: Response<T>): ApiResponse<T> {
        when {
            response.isSuccessful -> {
                return with(response.body()) {
                    if (this == null) {
                        ApiEmptyResponse()
                    } else {
                        ApiSuccessResponse(this)
                    }
                }
            }
            else -> with(response.body()?.toString()) {
                return if (this == null) {
                    ApiErrorResponse("")
                } else {
                    ApiErrorResponse(this)
                }
            }
        }
    }
}
