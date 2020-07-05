package com.devpraskov.skyengdictionary.models


class MeaningResponse : ArrayList<MeaningResponse.MeaningResponseItem>() {
    data class MeaningResponseItem(
        val definition: Definition? = null,
        val examples: List<Example?>? = null,
        val meaningsWithSimilarTranslation: List<MeaningsWithSimilarTranslation?>? = null
    ) {
        data class Definition(val text: String? = null)
        data class Example(val text: String? = null)
        data class MeaningsWithSimilarTranslation(
            val meaningId: Int? = null,
            val frequencyPercent: String? = null,
            val translation: Translation? = null
        ) {
            data class Translation(
                val text: String? = null,
                val note: String? = null
            )
        }
    }
}