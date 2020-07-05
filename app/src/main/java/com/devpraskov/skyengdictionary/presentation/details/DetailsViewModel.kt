package com.devpraskov.skyengdictionary.presentation.details

import android.app.Application
import androidx.lifecycle.*
import com.devpraskov.skyengdictionary.models.SearchUiModel
import com.devpraskov.skyengdictionary.repository.MainRepository
import com.devpraskov.skyengdictionary.utils.mapMeaningsToUi
import com.devpraskov.skyengdictionary.utils.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val mutableLiveData = MutableLiveData<SearchUiModel>()
    fun getHeaderLiveData(): LiveData<SearchUiModel> = mutableLiveData
    fun setHeaderLiveDat(model: SearchUiModel) { mutableLiveData.postValue(model) }

    private val mutableMeaningsLiveData = MutableLiveData<DetailsAction>()
    fun getMeaningsLiveData(): LiveData<DetailsAction> = mutableMeaningsLiveData

    fun loadMeanings(s: String) {
        mutableMeaningsLiveData.value = DetailsAction.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.wordMeaning(s)
            when (result) {
                is ApiResult.NetworkError -> {
                    mutableMeaningsLiveData.postValue(DetailsAction.Error(result.getError(getApplication())))
                }
                is ApiResult.Success -> {
                    mutableMeaningsLiveData.postValue(DetailsAction.Success(mapMeaningsToUi(result.value)))
                }
            }
        }
    }
}
