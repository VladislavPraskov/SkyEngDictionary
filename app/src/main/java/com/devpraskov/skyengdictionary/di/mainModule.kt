package com.devpraskov.skyengdictionary.di

import com.devpraskov.skyengdictionary.data.network.getApiClient
import com.devpraskov.skyengdictionary.data.network.getApiService
import com.devpraskov.skyengdictionary.data.network.getRetrofitBuilder
import com.devpraskov.skyengdictionary.presentation.details.DetailsViewModel
import com.devpraskov.skyengdictionary.presentation.search.SearchViewModel
import com.devpraskov.skyengdictionary.repository.MainRepository
import com.devpraskov.skyengdictionary.repository.MainRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    viewModel { SearchViewModel(get(), get()) }
    viewModel { DetailsViewModel(get(), get()) }
    factory<MainRepository> { MainRepositoryImpl(get()) }
    factory { getRetrofitBuilder() }
    factory { getApiClient() }
    factory { getApiService(get(), get()) }
}