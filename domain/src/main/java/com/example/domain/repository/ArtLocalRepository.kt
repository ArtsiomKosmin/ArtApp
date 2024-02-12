package com.example.domain.repository

import com.example.domain.models.ArtEntity
import kotlinx.coroutines.flow.Flow

interface ArtLocalRepository {
    suspend fun insertArt(art: ArtEntity)
    fun getAllArts(): Flow<List<ArtEntity>>
    suspend fun deleteCurrentArt(art: String)
}