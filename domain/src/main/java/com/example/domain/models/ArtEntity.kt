package com.example.domain.models

data class ArtEntity(
    val id: String,
    val title: String,
    val longTitle: String,
    val webImage: WebImage,
    val headerImage: HeaderImage
)

data class HeaderImage(
    val url: String
)

data class WebImage(
    val url: String
)