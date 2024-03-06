package com.example.artapp.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArtEntity
import com.example.domain.useCase.LocalArtsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val localArtsUseCase: LocalArtsUseCase) :
    ViewModel() {

    val allFavoriteArts = localArtsUseCase.getAllArts().asLiveData()

    fun favoriteOperations(artEntity: ArtEntity) {
        viewModelScope.launch {
            if (artEntity.isFavorite) {
                localArtsUseCase.deleteFromFavorite(artEntity.id)
            } else {
                localArtsUseCase.addAsFavorite(artEntity)
            }
        }
    }

    class DetailsViewModelFactory(private val localArtsUseCase: LocalArtsUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(localArtsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
