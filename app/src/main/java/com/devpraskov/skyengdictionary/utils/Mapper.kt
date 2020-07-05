package com.devpraskov.skyengdictionary.utils

import com.devpraskov.skyengdictionary.models.*
import kotlin.math.roundToInt
import kotlin.random.Random

fun mapSearchToUi(model: SearchResponse?): List<SearchUiModel> {
    return model?.mapNotNull {
        if (it.id != null && it.text != null)
            SearchUiModel(
                id = it.id,
                word = it.text.capitalize(),
                imageUrl = "https:${it.meanings?.getOrNull(0)?.imageUrl}",
                meaningId = it.meanings?.getOrNull(0)?.id.toString(),
                transcription = it.meanings?.getOrNull(0)?.transcription?.let {
                    if (it.isNotEmpty()) "[${it}]" else ""
                } ?: "",
                audioUrl = "https:${it.meanings?.getOrNull(0)?.soundUrl}"
            )
        else null
    } ?: listOf()
}

fun mapMeaningsToUi(model: MeaningResponse?): DetailsUi {
    return DetailsUi(
        definition = model?.getOrNull(0)?.definition?.text ?: "",
        meanings = model?.getOrNull(0)?.meaningsWithSimilarTranslation?.mapNotNull {
            MeaningUi(
                id = it?.meaningId ?: Random.nextInt(),
                frequencyPercent = it?.frequencyPercent?.toFloatOrNull()?.roundToInt() ?: 0,
                text = it?.translation?.text?.capitalize() ?: "",
                note = it?.translation?.note?.let { if (it.isNotEmpty()) "(${it})" else "" } ?: ""
            )
        }?.sortedByDescending { it.frequencyPercent } ?: listOf(),
        examples = model?.getOrNull(0)?.examples?.take(3)
            ?.joinToString(separator = "\n") { "- \"${it?.text?.capitalize()}\"" } ?: ""
    )
}