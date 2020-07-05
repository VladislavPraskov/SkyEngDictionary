package com.devpraskov.skyengdictionary.presentation.details

import com.devpraskov.skyengdictionary.models.DetailsUi

sealed class DetailsAction {
    object Loading : DetailsAction()
    data class Success(val model: DetailsUi) : DetailsAction()
    data class Error(val error: String) : DetailsAction()
}