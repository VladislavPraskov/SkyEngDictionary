package com.devpraskov.skyengdictionary.presentation.search

import com.devpraskov.skyengdictionary.models.SearchUiModel

sealed class SearchAction {
    object Loading : SearchAction()
    data class Success(val model: List<SearchUiModel>) : SearchAction()
    data class Error(val error: String) : SearchAction()
}