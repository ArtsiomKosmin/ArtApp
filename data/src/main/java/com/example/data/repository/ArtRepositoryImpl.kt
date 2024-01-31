package com.example.data.repository

import com.example.data.source.remote.ArtApi
import com.example.domain.entity.ArtObject
import com.example.domain.entity.ArtsResponse
import com.example.domain.repository.ArtRepository

class ArtRepositoryImpl(
    private val artApi: ArtApi
): ArtRepository {
    override suspend fun getArtObjects(): ArtsResponse {
        return artApi.getAllArts()
    }
}