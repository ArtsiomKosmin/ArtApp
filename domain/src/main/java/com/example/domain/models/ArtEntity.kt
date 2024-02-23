package com.example.domain.models

import java.io.Serializable

data class ArtEntity(
    val id: String,
    val title: String,
    val longTitle: String,
    val webImage: WebImage,
    var isFavorite: Boolean
): Serializable