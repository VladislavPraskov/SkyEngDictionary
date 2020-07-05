package com.devpraskov.skyengdictionary.models


data class DetailsUi(
    val definition: String,
    val meanings: List<MeaningUi>,
    val examples:String
)

data class MeaningUi(
    val id: Int,
    val frequencyPercent: Int,
    val text: String,
    val note: String
)