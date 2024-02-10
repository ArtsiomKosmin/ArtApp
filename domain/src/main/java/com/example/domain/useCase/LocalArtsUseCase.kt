package com.example.domain.useCase

import android.util.Log
import com.example.domain.models.ArtEntity
import com.example.domain.repository.ArtLocalRepository
import com.example.domain.useCase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalArtsUseCase(
    private val artLocalRepository: ArtLocalRepository
) : UseCase<Unit, List<ArtEntity>> {
    override suspend fun execute(params: Unit) = withContext(Dispatchers.IO) {
        artLocalRepository.getAllArts()
    }
    suspend fun addAsFavorite(art: ArtEntity) {
        Log.d("Check", "add As favorite")
        artLocalRepository.insertArt(art)
    }

}