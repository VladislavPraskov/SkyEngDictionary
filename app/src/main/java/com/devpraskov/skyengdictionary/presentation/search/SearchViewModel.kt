package com.devpraskov.skyengdictionary.presentation.search

import android.app.Application
import androidx.lifecycle.*
import com.devpraskov.skyengdictionary.repository.MainRepository
import com.devpraskov.skyengdictionary.utils.mapSearchToUi
import com.devpraskov.skyengdictionary.utils.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val mutableLiveData = MutableLiveData<SearchAction>()
    fun getLiveData(): LiveData<SearchAction> = mutableLiveData

    fun search(s: String) {
        mutableLiveData.value = SearchAction.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.searchWord(s)
            when (result) {
                is ApiResult.NetworkError -> {
                    mutableLiveData.postValue(SearchAction.Error(result.getError(getApplication())))
                }
                is ApiResult.Success -> {
                    mutableLiveData.postValue(SearchAction.Success(mapSearchToUi(result.value)))
                }
            }
        }
    }
}
