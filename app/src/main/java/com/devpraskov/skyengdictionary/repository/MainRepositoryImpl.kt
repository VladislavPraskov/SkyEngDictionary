package com.devpraskov.skyengdictionary.repository

import com.devpraskov.skyengdictionary.data.network.ApiService
import com.devpraskov.skyengdictionary.models.MeaningResponse
import com.devpraskov.skyengdictionary.models.SearchResponse
import com.devpraskov.skyengdictionary.utils.network.ApiResult
import com.devpraskov.skyengdictionary.utils.network.safeApiCall

class MainRepositoryImpl(private val api: ApiService) : MainRepository {

    override suspend fun searchWord(s: String): ApiResult<SearchResponse?> {
        return safeApiCall { api.searchWord(s) }
    }

    override suspend fun wordMeaning(ids: String): ApiResult<MeaningResponse?> {
        return safeApiCall { api.wordMeanings(ids) }
    }

}