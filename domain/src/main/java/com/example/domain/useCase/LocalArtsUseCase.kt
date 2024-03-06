package com.example.domain.useCase

import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtLocalRepository
import kotlinx.coroutines.flow.Flow

class LocalArtsUseCase(
    private val artLocalRepository: ArtLocalRepository
) {

    fun getAllArts(): Flow<List<ArtEntity>> {
        return artLocalRepository.getAllArts()
    }

    suspend fun addAsFavorite(art: ArtEntity) {
        artLocalRepository.insertArt(art)
    }

    suspend fun deleteFromFavorite(artId: String) {
        artLocalRepository.deleteCurrentArt(artId)
    }
}


