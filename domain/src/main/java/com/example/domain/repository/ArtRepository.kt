package com.example.domain.repository

import com.example.domain.models.ArtEntity

interface ArtRepository {
    suspend fun getArtObjects(): List<ArtEntity>
}