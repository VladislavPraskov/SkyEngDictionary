package com.devpraskov.skyengdictionary.utils.network

import android.content.Context
import androidx.annotation.StringRes
import com.devpraskov.skyengdictionary.R

sealed class ApiResult<out T> {

    data class Success<out T>(val value: T) : ApiResult<T>()

    data class NetworkError(
        val code: Int? = null,
        private val errorStr: String? = null,
        @StringRes
        private val errorRes: Int? = null
    ) : ApiResult<Nothing>() {
        fun getError(context: Context): String {
            return errorStr
                ?: errorRes?.let { context.getString(it) }
                ?: context.getString(R.string.unknown_error)
        }
    }
}
