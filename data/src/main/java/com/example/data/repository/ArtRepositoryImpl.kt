package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.source.remote.ArtApi
import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtRepository

class ArtRepositoryImpl(
    private val artApi: ArtApi
): ArtRepository {
    override suspend fun getArtObjects(): List<ArtEntity> {
        val allArts = artApi.getAllArts()
        return allArts.artObjects.map { it.toDomain() }
    }
}