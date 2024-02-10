package com.example.domain.repository

import com.example.domain.models.ArtEntity

interface ArtRemoteRepository {
    suspend fun getArtObjects(): List<ArtEntity>
}