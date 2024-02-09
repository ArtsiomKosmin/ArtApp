package com.example.domain.models

import java.io.Serializable

data class ArtEntity(
    val id: String,
    val title: String,
    val longTitle: String,
    val webImage: WebImage,
    val headerImage: HeaderImage,
    val isFavorite: Boolean
): Serializable

data class HeaderImage(
    val url: String
)

data class WebImage(
    val url: String
)