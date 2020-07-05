package com.devpraskov.skyengdictionary.repository

import com.devpraskov.skyengdictionary.models.MeaningResponse
import com.devpraskov.skyengdictionary.models.SearchResponse
import com.devpraskov.skyengdictionary.utils.network.ApiResult

interface MainRepository {
    suspend fun searchWord(s: String): ApiResult<SearchResponse?>
    suspend fun wordMeaning(ids: String): ApiResult<MeaningResponse?>
}