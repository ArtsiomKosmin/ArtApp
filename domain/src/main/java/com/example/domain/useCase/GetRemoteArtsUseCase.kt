package com.example.domain.useCase

import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtRemoteRepository
import com.example.domain.useCase.base.UseCase

class GetRemoteArtsUseCase(
    private val artRemoteRepository: ArtRemoteRepository
): UseCase <Unit, List<ArtEntity>> {
    override suspend fun execute(params: Unit) : List<ArtEntity> {
        return artRemoteRepository.getArtObjects()
    }
}