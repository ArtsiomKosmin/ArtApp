package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.source.remote.ArtApi
import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtRemoteRepository

class ArtRemoteRepositoryImpl(
    private val artApi: ArtApi
): ArtRemoteRepository {
    override suspend fun getArtObjects(page: Int): List<ArtEntity> {
        val allArts = artApi.getAllArts(page = page)
        if (allArts.isSuccessful) {
            return allArts.body()!!.artObjects.map { it.toDomain() }
        } else {
            throw Exception()
        }

    }
}