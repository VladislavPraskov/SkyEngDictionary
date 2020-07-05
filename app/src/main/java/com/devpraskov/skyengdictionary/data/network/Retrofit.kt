package com.devpraskov.skyengdictionary.data.network

import com.devpraskov.skyengdictionary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

    const val BASE_URL_DEV = "https://dictionary.skyeng.ru/api/public/v1/"

    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    fun getRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder().baseUrl(BASE_URL_DEV).addConverterFactory(GsonConverterFactory.create())

    fun getApiClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(getLoggingInterceptor())
        .build()

    fun getApiService(retrofitBuilder: Retrofit.Builder, client: OkHttpClient): ApiService =
        retrofitBuilder.client(client).build().create(ApiService::class.java)