package com.example.domain

import com.example.domain.entity.ArtObject
import com.example.domain.entity.ArtsResponse
import com.example.domain.repository.ArtRepository
import com.example.domain.useCase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke

class GetArtsUseCase(
    private val artRepository: ArtRepository
): UseCase<Unit, ArtsResponse> {
    override suspend fun execute(params: Unit): ArtsResponse = (Dispatchers.IO) {
        artRepository.getArtObjects()
    }
}