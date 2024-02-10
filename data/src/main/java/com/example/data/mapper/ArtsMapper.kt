package com.example.data.mapper

import com.example.data.models.ArtDto
import com.example.data.source.local.entity.ArtEntityDB
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

//internal fun ArtEntityLocal.toArtEntityDB() = ArtEntityDB(
//    id = null,
//    artId = id,
//    title = title,
//    longTitle = longTitle,
//    webImageUrl = webImage.url,
//    headerImageUrl = headerImage.url,
//    isFavorite = true
//)

internal fun ArtEntity.toArtEntityDB() = ArtEntityDB(
    null,
    artId = id,
    title = title,
    longTitle = longTitle,
    webImageUrl = webImage.url,
    headerImageUrl = headerImage.url,
    isFavorite = true
)

internal fun ArtEntityDB.toDomain() = ArtEntity(
    id = artId,
    title = title,
    longTitle = longTitle,
    WebImage(url = webImageUrl),
    HeaderImage(url = headerImageUrl),
    isFavorite = true
)