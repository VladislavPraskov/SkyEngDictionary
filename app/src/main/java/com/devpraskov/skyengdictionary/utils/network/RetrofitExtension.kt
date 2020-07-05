package com.devpraskov.skyengdictionary.utils.network

import com.devpraskov.skyengdictionary.R
import retrofit2.HttpException
import java.io.IOException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */

const val NETWORK_ERROR_CODE = 503

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return try {
        ApiResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is IOException -> {
                ApiResult.NetworkError(
                    code = NETWORK_ERROR_CODE,
                    errorRes = R.string.error_connection
                )
            }
            is HttpException -> {
                val code = throwable.code()
                var errorResponse = convertErrorBody(throwable)
                val resId = if (throwable.code() in 500..599) {
                    errorResponse = null
                    R.string.backend_connection_error
                } else R.string.unknown_error
                ApiResult.NetworkError(code = code, errorStr = errorResponse, errorRes = resId)
            }
            else -> {
                ApiResult.NetworkError(code = null, errorRes = R.string.unknown_error)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        null
    }
}

