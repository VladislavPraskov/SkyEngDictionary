package com.devpraskov.skyengdictionary.models


class SearchResponse : ArrayList<SearchResponse.SearchResponseItem>(){
    data class SearchResponseItem(
        val id: Long? = null,
        val text: String? = null,
        val meanings: List<Meaning?>? = null
    ) {
        data class Meaning(
            val id: Long? = null,
            val imageUrl: String? = null,
            val transcription: String? = null,
            val soundUrl: String? = null
        )
    }
}