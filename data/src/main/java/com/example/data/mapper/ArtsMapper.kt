package com.example.data.mapper

import com.example.data.source.remote.models.ArtDto
import com.example.data.source.local.entity.ArtEntityDB
import com.example.domain.models.ArtEntity
import com.example.domain.models.WebImage

internal fun ArtDto.toDomain() = ArtEntity(
    id = id,
    title = title,
    longTitle = longTitle,
    webImage = WebImage(url = webImage.url),
    isFavorite = false
    )

internal fun ArtEntity.toArtEntityDB() = ArtEntityDB(
    null,
    artId = id,
    title = title,
    longTitle = longTitle,
    webImageUrl = webImage.url,
    isFavorite = true
)

internal fun ArtEntityDB.toDomain() = ArtEntity(
    id = artId,
    title = title,
    longTitle = longTitle,
    WebImage(url = webImageUrl),
    isFavorite = true
)