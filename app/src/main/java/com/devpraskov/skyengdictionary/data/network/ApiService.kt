package com.devpraskov.skyengdictionary.data.network

import com.devpraskov.skyengdictionary.models.MeaningResponse
import com.devpraskov.skyengdictionary.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    suspend fun searchWord(@Query("search") word: String): SearchResponse

    @GET("meanings")
    suspend fun wordMeanings(@Query("ids") ids: String): MeaningResponse

}