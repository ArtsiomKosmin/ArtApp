package com.example.data.mapper

import com.example.data.models.ArtDto
import com.example.domain.models.ArtEntity
import com.example.domain.models.HeaderImage
import com.example.domain.models.WebImage

internal fun ArtDto.toDomain() = ArtEntity(
    id = id,
    title = title,
    longTitle = longTitle,
    webImage = WebImage(url = webImage.url),
    headerImage = HeaderImage(url = headerImage.url),
    isFavorite = false
    )