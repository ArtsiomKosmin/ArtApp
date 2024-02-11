package com.example.domain.repository

import com.example.domain.models.ArtEntity

interface ArtLocalRepository {
    suspend fun insertArt(art: ArtEntity)
    fun getAllArts(): List<ArtEntity>
    suspend fun deleteCurrentArt(art: String)
}