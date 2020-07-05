package com.devpraskov.skyengdictionary.utils

import com.devpraskov.skyengdictionary.models.MeaningResponse
import com.devpraskov.skyengdictionary.models.MeaningResponse.*
import com.devpraskov.skyengdictionary.models.MeaningResponse.MeaningResponseItem.*
import com.devpraskov.skyengdictionary.models.MeaningResponse.MeaningResponseItem.MeaningsWithSimilarTranslation.*
import com.devpraskov.skyengdictionary.models.SearchResponse
import com.devpraskov.skyengdictionary.models.SearchResponse.SearchResponseItem
import com.devpraskov.skyengdictionary.models.SearchResponse.SearchResponseItem.Meaning
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as Is

class MapperKtTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun mapSearchToUi_NullModel() {
        val model = SearchResponse()
        val result = mapSearchToUi(model) //не должно падать
        result.forEach {
            assertThat(it.word, equalTo("-"))
            assertThat(it.id, notNullValue())
            assertThat(it.transcription, equalTo(""))
            assertThat(it.meaningId, Is(nullValue()))
        }
    }

    @Test
    fun mapSearchToUi() {
        val model = getMockSearchUiModel()
        val result = mapSearchToUi(model) //не должно падать
        result[0].let {
            assertThat(it.id, equalTo(20495L))
            assertThat(it.word, equalTo("Have")) //upper case
            assertThat(
                it.imageUrl,
                startsWith("https:") //https:
            )
            assertThat(it.transcription, equalTo("[hæv]"))
            assertThat(it.meaningId, equalTo(20495.toString()))
            assertThat(
                it.audioUrl,
                startsWith("https:")
            )
        }
        result[1].let {
            assertThat(it.id, equalTo(20495L))
            assertThat(it.word, equalTo("6h"))
            assertThat(it.transcription, equalTo(""))
        }
    }

    private fun getMockSearchUiModel(): SearchResponse {
        val array = SearchResponse()
        array.add(
            SearchResponseItem(
                id = 20495,
                text = "have",
                meanings = listOf(
                    Meaning(
                        id = 20495,
                        imageUrl = "//d2zkmv5t5kao9.cloudfront.net/images/dbd5ac57d54c73301d56da354b163ce4.jpeg?w=640&h=480",
                        transcription = "hæv",
                        soundUrl = "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=h%C3%A6v&transcription=1"
                    )
                )
            )
        )
        array.add(
            SearchResponseItem(
                id = 20495,
                text = "6h",
                meanings = listOf(
                    Meaning(
                        id = 20495,
                        imageUrl = "//d2zkmv5t5kao9.cloudfront.net/images/dbd5ac57d54c73301d56da354b163ce4.jpeg?w=640&h=480",
                        transcription = "",
                        soundUrl = "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=h%C3%A6v&transcription=1"
                    )
                )
            )
        )
        return array
    }

    @Test
    fun mapMeaningsToUi_NullModel() {
        val model = MeaningResponse()
        val result = mapMeaningsToUi(model) //не должно падать
    }

    @Test
    fun mapMeaningsToUi() {
        val model = getMockDetailsUiModel()
        val result = mapMeaningsToUi(model)
        result.apply {
            assertThat(
                definition,
                equalTo("To possess, own, or hold, either in a concrete or an abstract sense.")
            )
            assertThat(
                examples,
                equalTo("- \"Do you [have] time to finish the report today?\"\n- \"Do you [have] time to finish the report today?\"\n- \"Do you [have] time to finish the report today?\"")
            )
            meanings[0].apply {
                assertThat(id, equalTo(20495))
                assertThat(frequencyPercent, equalTo(63))
                assertThat(text, equalTo("Включать в себя"))
                assertThat(note, equalTo("(иметь)"))
            }
        }
    }

    private fun getMockDetailsUiModel(): MeaningResponse? {
        val array = MeaningResponse()
        array.add(
            MeaningResponseItem(
                definition = Definition(text = "To possess, own, or hold, either in a concrete or an abstract sense."),
                examples = listOf(
                    Example(text = "Do you [have] time to finish the report today?"),
                    Example(text = "Do you [have] time to finish the report today?"),
                    Example(text = "Do you [have] time to finish the report today?")
                ),
                meaningsWithSimilarTranslation = listOf(
                    MeaningsWithSimilarTranslation(
                        meaningId = 20495,
                        frequencyPercent = "62.9",
                        translation = Translation(text = "включать в себя", note = "иметь")
                    )
                )
            )
        )
        array.add(
            MeaningResponseItem(
                definition = Definition(text = "To possess, own, or hold, either in a concrete or an abstract sense."),
                examples = listOf(
                    Example(text = "Do you [have] time to finish the report today?")
                ),
                meaningsWithSimilarTranslation = listOf(
                    MeaningsWithSimilarTranslation(
                        meaningId = null,
                        frequencyPercent = null,
                        translation = Translation(text = null, note = null)
                    )
                )
            )
        )
        return array
    }
}