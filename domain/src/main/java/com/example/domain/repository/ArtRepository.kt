package com.example.domain.repository

import com.example.domain.entity.ArtsResponse

interface ArtRepository {
    suspend fun getArtObjects(): ArtsResponse
}