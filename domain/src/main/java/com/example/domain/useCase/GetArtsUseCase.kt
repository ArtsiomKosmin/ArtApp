package com.example.domain.useCase

import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtRepository
import com.example.domain.useCase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetArtsUseCase(
    private val artRepository: ArtRepository
): UseCase<Unit, List<ArtEntity>> {
    override suspend fun execute(params: Unit) = withContext(Dispatchers.IO) {
        artRepository.getArtObjects()
    }
}