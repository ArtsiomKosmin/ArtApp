package com.example.data.repository

import com.example.data.mapper.toArtEntityDB
import com.example.data.mapper.toDomain
import com.example.data.source.local.dao.ArtDao
import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtLocalRepositoryImpl @Inject constructor(
    private val dao: ArtDao
): ArtLocalRepository {
    override suspend fun insertArt(art: ArtEntity) {
        return dao.insertArt(art.toArtEntityDB())
    }

    override fun getAllArts(): Flow<List<ArtEntity>> {
        return dao.getAllArts().map { it.map { it.toDomain() } }
    }

    override suspend fun deleteCurrentArt(artId: String) {
        return dao.deleteCurrentArt(artId)
    }
}