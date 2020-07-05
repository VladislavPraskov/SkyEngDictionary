package com.devpraskov.skyengdictionary.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUiModel(
    val id: Long,
    val word: String,
    val imageUrl: String?,
    val transcription: String,
    val meaningId: String,
    val audioUrl: String?
) : Parcelable